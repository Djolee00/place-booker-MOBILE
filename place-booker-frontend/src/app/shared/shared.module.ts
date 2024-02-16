import { NgModule } from '@angular/core';
import { LocationPickerComponent } from './pickers/location-picker/location-picker.component';
import { MapModelComponent } from './map-model/map-model.component';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ImagePickerComponent } from './pickers/image-picker/image-picker.component';

@NgModule({
  declarations: [
    LocationPickerComponent,
    MapModelComponent,
    ImagePickerComponent,
  ],
  imports: [CommonModule, IonicModule],
  exports: [LocationPickerComponent, MapModelComponent, ImagePickerComponent],
})
export class SharedModule {}
