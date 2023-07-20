import { NgModule } from '@angular/core';
import { LocationPickerComponent } from './pickers/location-picker/location-picker.component';
import { MapModelComponent } from './map-model/map-model.component';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

@NgModule({
  declarations: [LocationPickerComponent, MapModelComponent],
  imports: [CommonModule, IonicModule],
  exports: [LocationPickerComponent, MapModelComponent],
})
export class SharedModule {}
