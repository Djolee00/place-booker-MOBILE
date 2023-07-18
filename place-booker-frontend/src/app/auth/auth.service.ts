import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, from, last, map, tap } from 'rxjs';
import { User } from './user.model';
import { HttpClient } from '@angular/common/http';
import { Preferences } from '@capacitor/preferences';
import { environment } from 'src/environments/environment';

export interface AuthResponseData {
  userId: number;
  token: string;
  expirationTime: string;
  email: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService implements OnDestroy {
  private _user = new BehaviorSubject<User | null>(null);
  private activeLogoutTimeout: any;

  constructor(private http: HttpClient) {}

  ngOnDestroy(): void {}

  signup(
    email: string,
    password: string,
    firstName: string,
    lastName: string,
    age: number
  ) {
    return this.http
      .post<AuthResponseData>(`${environment.apiUrl}/auth/signup`, {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        age: age,
      })
      .pipe(tap(this.setUserData.bind(this)));
  }

  login(email: string, password: string) {
    return this.http
      .post<AuthResponseData>(`${environment.apiUrl}/auth/signin`, {
        email: email,
        password: password,
      })
      .pipe(tap(this.setUserData.bind(this)));
  }

  logout() {
    if (this.activeLogoutTimeout) {
      clearTimeout(this.activeLogoutTimeout);
    }
    this._user.next(null);
    Preferences.remove({ key: 'authData' });
  }

  autoLogin() {
    return from(Preferences.get({ key: 'authData' })).pipe(
      map((storedData) => {
        if (!storedData || !storedData.value) {
          return null;
        }
        const parsedData = JSON.parse(storedData.value) as {
          token: string;
          tokenExpirationDate: string;
          userId: number;
          email: string;
        };
        const expirationTime = new Date(parsedData.tokenExpirationDate);
        if (expirationTime <= new Date()) {
          return null;
        }
        const user = new User(
          parsedData.userId,
          parsedData.email,
          parsedData.token,
          expirationTime
        );
        return user;
      }),
      tap((user) => {
        if (user) {
          this._user.next(user);
          this.autoLogout(user.tokenDuration);
        }
      }),
      map((user) => {
        return !!user;
      })
    );
  }

  private autoLogout(duration: number) {
    if (this.activeLogoutTimeout) {
      clearTimeout(this.activeLogoutTimeout);
    }
    this.activeLogoutTimeout = setTimeout(() => {
      this.logout();
    }, duration);
  }

  private setUserData(userData: AuthResponseData) {
    console.log(userData);
    const expirationTime = new Date(userData.expirationTime);

    const user = new User(
      userData.userId,
      userData.email,
      userData.token,
      expirationTime
    );
    this._user.next(user);
    this.autoLogout(user.tokenDuration);
    this.storeAuthData(
      userData.userId,
      userData.token,
      expirationTime.toISOString(),
      userData.email
    );
  }

  private async storeAuthData(
    userId: number,
    token: string,
    tokenExpirationDate: string,
    email: string
  ) {
    const data = JSON.stringify({
      userId: userId,
      token: token,
      tokenExpirationDate: tokenExpirationDate,
      email: email,
    });
    await Preferences.set({ key: 'authData', value: data });
  }

  get userIsAuthenticated() {
    return this._user.asObservable().pipe(
      map((user) => {
        if (user) {
          return !!user.token;
        } else {
          return false;
        }
      })
    );
  }

  get userId() {
    return this._user.asObservable().pipe(
      map((user) => {
        if (user) {
          return user.id;
        } else {
          return null;
        }
      })
    );
  }

  get token() {
    return this._user.asObservable().pipe(
      map((user) => {
        if (user) {
          return user.token;
        } else {
          return null;
        }
      })
    );
  }
}
