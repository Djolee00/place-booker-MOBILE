import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  map,
  of,
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
  placeImageUrl: string | null;
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
            placeData.placeLocation,
            placeData.placeImageUrl
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
        let imageUrl: string;
        if (placeData.placeImageUrl) {
          imageUrl = 'data:image/jpeg;base64,' + placeData.placeImageUrl;
        }

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
          },
          imageUrl!
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
    location: PlaceLocation,
    image: Blob
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
        newPlace = new Place(
          null,
          title,
          description,
          imageUrl,
          price,
          dateFrom,
          dateTo,
          user,
          location,
          null
        );
        const newPlaceJson = JSON.stringify(newPlace);
        const newPlaceJsonBlob = new Blob([newPlaceJson], {
          type: 'application/json',
        });
        const formData = new FormData();
        formData.append('place', newPlaceJsonBlob);

        if (image.type === 'image/png') {
          formData.append('image', image, 'Image of place.png');
        } else {
          formData.append('image', image, 'Image of place.jpeg');
        }
        const headers = this.createAuthHeader(token);
        headers.append('Content-Type', 'multipart/mixed');
        return this.http
          .post<{ id: number }>(`${environment.apiUrl}/places`, formData, {
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

  updatePlace(placeId: number, title: string, description: string) {
    let updatedPlaces: Place[];
    let fetchedToken: string | null;
    return this.authService.token.pipe(
      take(1),
      switchMap((token) => {
        fetchedToken = token;
        return this.places;
      }),
      take(1),
      switchMap((places) => {
        if (!places || places.length === 0) {
          return this.fetchPlaces();
        } else {
          return of(places);
        }
      }),
      switchMap((places) => {
        const updatedPlaceIndex = places.findIndex((pl) => pl.id == placeId);
        updatedPlaces = [...places];
        const oldPlace = updatedPlaces[updatedPlaceIndex];
        updatedPlaces[updatedPlaceIndex] = new Place(
          oldPlace.id,
          title,
          description,
          oldPlace.imageUrl,
          oldPlace.price,
          oldPlace.availableFrom,
          oldPlace.availableTo,
          oldPlace.user,
          oldPlace.placeLocation,
          oldPlace.placeImageUrl
        );
        const headers = this.createAuthHeader(fetchedToken);
        return this.http.put(
          `${environment.apiUrl}/places/${placeId}`,
          {
            ...updatedPlaces[updatedPlaceIndex],
            id: null,
          },
          {
            headers: headers,
          }
        );
      }),
      tap(() => {
        this._places.next(updatedPlaces);
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
