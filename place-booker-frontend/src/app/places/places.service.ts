import { Injectable } from '@angular/core';
import { BehaviorSubject, map, switchMap, take, tap } from 'rxjs';
import { Place } from './place.model';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

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

  constructor(private authService: AuthService, private http: HttpClient) {}

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
            placeData.id.toString(),
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

  get places() {
    return this._places.asObservable();
  }

  private createAuthHeader(token: string | null): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }
}
