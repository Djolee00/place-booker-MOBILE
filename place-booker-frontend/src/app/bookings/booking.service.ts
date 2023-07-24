import { Injectable } from '@angular/core';
import { BehaviorSubject, map, switchMap, take, tap } from 'rxjs';
import { Booking } from './booking.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Place, User } from '../places/place.model';
import { environment } from 'src/environments/environment';

interface BookingData {
  id: number;
  title: string;
  firstName: string;
  lastName: string;
  guestNumber: number;
  bookedFrom: string;
  bookedTo: string;
  user: User;
  place: Place;
}

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private _bookings = new BehaviorSubject<Booking[]>([]);

  get bookings() {
    return this._bookings.asObservable();
  }

  constructor(private authService: AuthService, private http: HttpClient) {}

  addBooking(
    place: Place,
    title: string,
    firstName: string,
    lastName: string,
    guestNumber: number,
    dateFrom: Date,
    dateTo: Date
  ) {
    let generatedId: number;
    let newBooking: Booking;
    let fetchedUserId: number;
    return this.authService.userId.pipe(
      take(1),
      switchMap((userId) => {
        if (!userId) {
          throw new Error('No user id found!');
        }
        fetchedUserId = userId;
        return this.authService.token;
      }),
      take(1),
      switchMap((token) => {
        const user: User = {
          id: fetchedUserId,
          email: null,
          firstName: null,
          lastName: null,
          age: null,
        };
        newBooking = new Booking(
          null,
          place,
          user,
          title,
          firstName,
          lastName,
          guestNumber,
          dateFrom,
          dateTo
        );
        const headers = this.createAuthHeader(token);
        return this.http.post<BookingData>(
          `${environment.apiUrl}/bookings`,
          newBooking,
          {
            headers: headers,
          }
        );
      }),
      switchMap((resData) => {
        generatedId = resData.id;
        return this.bookings;
      }),
      take(1),
      tap((bookings) => {
        newBooking.id = generatedId;
        this._bookings.next(bookings.concat(newBooking));
      })
    );
  }

  cancelBooking(bookingId: number) {
    return this.authService.token.pipe(
      take(1),
      switchMap((token) => {
        const headers = this.createAuthHeader(token);
        return this.http.delete(`${environment.apiUrl}/bookings/${bookingId}`, {
          headers: headers,
        });
      }),
      switchMap(() => {
        return this.bookings;
      }),
      take(1),
      tap((bookings) => {
        this._bookings.next(bookings.filter((b) => b.id !== bookingId));
      })
    );
  }

  fetchBookings() {
    let fetchedUserId: number;
    return this.authService.userId.pipe(
      take(1),
      switchMap((userId) => {
        if (!userId) {
          throw new Error('User not found');
        }
        fetchedUserId = userId;
        return this.authService.token;
      }),
      take(1),
      switchMap((token) => {
        const headers = this.createAuthHeader(token);
        return this.http.get<BookingData[]>(
          `${environment.apiUrl}/bookings?userId=${fetchedUserId}`,
          {
            headers: headers,
          }
        );
      }),
      map((bookingData) => {
        return bookingData.map((bookingData: BookingData) => {
          return new Booking(
            bookingData.id,
            bookingData.place,
            bookingData.user,
            bookingData.title,
            bookingData.firstName,
            bookingData.lastName,
            bookingData.guestNumber,
            new Date(bookingData.bookedFrom),
            new Date(bookingData.bookedTo)
          );
        });
      }),
      tap((bookings) => {
        this._bookings.next(bookings);
      })
    );
  }

  private createAuthHeader(token: string | null): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }
}
