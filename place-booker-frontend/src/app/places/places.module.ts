import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { PlacesPageRoutingModule } from './places-routing.module';

import { PlacesPage } from './places.page';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    PlacesPageRoutingModule,
    SharedModule,
  ],
  declarations: [PlacesPage],
})
export class PlacesPageModule {}
