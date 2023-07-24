import { Component, OnDestroy, OnInit } from '@angular/core';
import { Place } from '../place.model';
import { Subscription, take } from 'rxjs';
import { PlacesService } from '../places.service';
import { Router } from '@angular/router';
import { IonItemSliding } from '@ionic/angular';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.page.html',
  styleUrls: ['./offers.page.scss'],
})
export class OffersPage implements OnInit, OnDestroy {
  offers: Place[] = [];
  isLoading = false;
  userId: number | null;
  private placesSub: Subscription;

  constructor(
    private placesService: PlacesService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.placesSub = this.placesService.places.subscribe((places) => {
      this.offers = places;
    });
    this.authService.userId.subscribe((userId) => {
      this.userId = userId;
    });
  }

  onEdit(offerId: number, slidingItem: IonItemSliding) {
    slidingItem.close();
    this.router.navigate(['/', 'places', 'tabs', 'offers', 'edit', offerId]);
    console.log('Editing item: ', offerId);
  }

  ngOnDestroy() {
    if (this.placesSub) {
      this.placesSub.unsubscribe();
    }
  }

  ionViewWillEnter() {
    this.isLoading = true;
    this.placesService.fetchPlaces().subscribe(() => {
      this.isLoading = false;
    });
  }
}
