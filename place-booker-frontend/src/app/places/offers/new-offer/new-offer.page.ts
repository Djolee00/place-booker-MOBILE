import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PlacesService } from '../../places.service';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { PlaceLocation } from '../../location.model';

function base64toBlob(base64Data: string, contentType: string) {
  contentType = contentType || '';
  const sliceSize = 1024;
  const byteCharacters = atob(base64Data);
  const bytesLength = byteCharacters.length;
  const slicesCount = Math.ceil(bytesLength / sliceSize);
  const byteArrays = new Array(slicesCount);

  for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
    const begin = sliceIndex * sliceSize;
    const end = Math.min(begin + sliceSize, bytesLength);

    const bytes = new Array(end - begin);
    for (let offset = begin, i = 0; offset < end; ++i, ++offset) {
      bytes[i] = byteCharacters[offset].charCodeAt(0);
    }
    byteArrays[sliceIndex] = new Uint8Array(bytes);
  }
  return new Blob(byteArrays, { type: contentType });
}

@Component({
  selector: 'app-new-offer',
  templateUrl: './new-offer.page.html',
  styleUrls: ['./new-offer.page.scss'],
})
export class NewOfferPage implements OnInit {
  form: FormGroup;

  constructor(
    private placesService: PlacesService,
    private router: Router,
    private loadingCtrl: LoadingController,
    private alertCtrl: AlertController
  ) {}

  ngOnInit() {
    this.form = new FormGroup({
      title: new FormControl(null, {
        updateOn: 'blur',
        validators: [Validators.required],
      }),
      description: new FormControl(null, {
        updateOn: 'blur',
        validators: [Validators.required, Validators.maxLength(180)],
      }),
      price: new FormControl(null, {
        updateOn: 'blur',
        validators: [Validators.required, Validators.min(1)],
      }),
      dateFrom: new FormControl(new Date().toISOString(), {
        updateOn: 'blur',
        validators: [Validators.required],
      }),
      dateTo: new FormControl(new Date().toISOString(), {
        updateOn: 'blur',
        validators: [Validators.required],
      }),
      location: new FormControl(null, {
        validators: [Validators.required],
      }),
      imageUrl: new FormControl(null, {}),
      image: new FormControl(null),
    });
  }

  onCreateOffer() {
    if (!this.form.valid || !this.form.get('image')!.value) {
      return;
    }
    console.log(this.form.value);
    this.loadingCtrl
      .create({
        message: 'Creating place...',
      })
      .then((loadingEl) => {
        loadingEl.present();
        console.log(this.form.value.location);
        this.placesService
          .addPlace(
            this.form.value.title,
            this.form.value.description,
            +this.form.value.price,
            new Date(this.form.value.dateFrom),
            new Date(this.form.value.dateTo),
            this.form.value.imageUrl,
            this.form.value.location
          )
          .subscribe(
            () => {
              loadingEl.dismiss();
              this.form.reset();
              this.router.navigate(['/places/tabs/offers']);
            },
            (error) => {
              loadingEl.dismiss();
              this.alertCtrl
                .create({
                  header: 'Invalid date range!',
                  message: 'Could not save place.',
                  buttons: [
                    {
                      text: 'Okay',
                    },
                  ],
                })
                .then((alertEl) => alertEl.present());
            }
          );
      });
  }

  onLocationPicked(location: PlaceLocation) {
    this.form.patchValue({ location: location });
  }

  onImagePicked(imageData: string | File) {
    let imageFile;
    if (typeof imageData === 'string') {
      console.log(imageData);
      try {
        if (imageData.startsWith('data:image/png;base64,')) {
          imageFile = base64toBlob(
            imageData.replace('data:image/png;base64,', ''),
            'image/png'
          );
        } else {
          imageFile = base64toBlob(
            imageData.replace('data:image/jpeg;base64,', ''),
            'image/jpeg'
          );
        }
      } catch (error) {
        console.log(error);
        return;
      }
    } else {
      imageFile = imageData;
    }
    this.form.patchValue({ image: imageFile });
  }
}
