import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  map,
  switchMap,
  take,
  tap,
  throwError,
} from 'rxjs';
import { Place, User } from './place.model';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PlaceLocation } from './location.model';
import { ToastController } from '@ionic/angular';

interface PlaceResponse {
  id: number;
  title: string;
  description: string;
  imageUrl: string;
  price: number;
  availableFrom: string;
  availableTo: string;
  user: {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    age: number;
  };
  placeLocation: {
    address: string;
    staticMapImageUrl: string;
    lat: number;
    lng: number;
  };
}

@Injectable({
  providedIn: 'root',
})
export class PlacesService {
  private _places = new BehaviorSubject<Place[]>([]);

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private toastCtrl: ToastController
  ) {}

  fetchPlaces() {
    return this.authService.token.pipe(
      take(1),
      switchMap((token) => {
        const headers = this.createAuthHeader(token);
        return this.http.get<PlaceResponse[]>(`${environment.apiUrl}/places`, {
          headers: headers,
        });
      }),
      map((placesData: PlaceResponse[]) => {
        return placesData.map((placeData: PlaceResponse) => {
          return new Place(
            placeData.id,
            placeData.title,
            placeData.description,
            placeData.imageUrl,
            placeData.price,
            new Date(placeData.availableFrom),
            new Date(placeData.availableTo),
            placeData.user,
            placeData.placeLocation
          );
        });
      }),
      tap((places) => {
        this._places.next(places);
        console.log(places);
      })
    );
  }

  getPlace(id: number) {
    return this.authService.token.pipe(
      take(1),
      switchMap((token) => {
        const headers = this.createAuthHeader(token);
        return this.http.get<PlaceResponse>(
          `${environment.apiUrl}/places/${id}`,
          {
            headers: headers,
          }
        );
      }),
      map((placeData) => {
        return new Place(
          placeData.id,
          placeData.title,
          placeData.description,
          placeData.imageUrl,
          placeData.price,
          new Date(placeData.availableFrom),
          new Date(placeData.availableTo),
          placeData.user,
          {
            ...placeData.placeLocation,
            staticMapImageUrl:
              placeData.placeLocation.staticMapImageUrl +
              `&key=${environment.googleMapsAPIKey}`,
          }
        );
      })
    );
  }

  addPlace(
    title: string,
    description: string,
    price: number,
    dateFrom: Date,
    dateTo: Date,
    imageUrl: string,
    location: PlaceLocation
  ) {
    let generatedId: number;
    let fetchedUserId: number | null;
    let newPlace: Place;
    return this.authService.userId.pipe(
      take(1),
      switchMap((userId) => {
        fetchedUserId = userId;
        return this.authService.token;
      }),
      take(1),
      switchMap((token) => {
        if (!fetchedUserId) {
          throw new Error('No user found');
        }
        const user: User = {
          id: fetchedUserId,
          email: null,
          firstName: null,
          lastName: null,
          age: null,
        };
        console.log(location);
        newPlace = new Place(
          null,
          title,
          description,
          imageUrl,
          price,
          dateFrom,
          dateTo,
          user,
          location
        );
        const headers = this.createAuthHeader(token);

        return this.http
          .post<{ id: number }>(`${environment.apiUrl}/places`, newPlace, {
            headers: headers,
          })
          .pipe(
            catchError((error) => {
              console.error(error);
              const errorMessage =
                'An error occurred while creating the place.';
              this.toastCtrl
                .create({
                  message: errorMessage,
                  duration: 3000,
                  position: 'bottom',
                })
                .then((toast) => toast.present());

              return throwError(error);
            })
          );
      }),
      switchMap((resData) => {
        generatedId = resData.id;
        return this.places;
      }),
      take(1),
      tap((places) => {
        newPlace.id = generatedId;
        this._places.next(places.concat(newPlace));
      })
    );
  }

  get places() {
    return this._places.asObservable();
  }

  private createAuthHeader(token: string | null): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }
}
