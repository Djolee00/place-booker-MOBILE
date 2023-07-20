import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { ModalController } from '@ionic/angular';
import { google } from 'google-maps';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-map-model',
  templateUrl: './map-model.component.html',
  styleUrls: ['./map-model.component.scss'],
})
export class MapModelComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('map') mapElementRef: ElementRef;
  @Input() center = { lat: -34.397, lng: 150.644 };
  @Input() selectable = true;
  @Input() closeButtonText = 'Cancel';
  @Input() title = 'Pick Location';
  clickListener: any;
  googleMaps: any;

  constructor(
    private modalCtrl: ModalController,
    private renderer: Renderer2
  ) {}

  ngOnInit() {}

  onCancel() {
    this.modalCtrl.dismiss();
  }

  ngAfterViewInit() {
    this.getGoogleMaps()
      .then((googleMaps) => {
        this.googleMaps = googleMaps;
        const mapEl = this.mapElementRef.nativeElement;
        const map = new googleMaps.Map(mapEl, {
          center: {
            lat: Number(this.center.lat),
            lng: Number(this.center.lng),
          },
          zoom: 16,
        });
        googleMaps.event.addListenerOnce(map, 'idle', () => {
          this.renderer.addClass(mapEl, 'visible');
        });

        if (this.selectable) {
          this.clickListener = map.addListener(
            'click',
            (event: google.maps.MouseEvent) => {
              const selectedCoords = {
                lat: event.latLng.lat(),
                lng: event.latLng.lng(),
              };
              this.modalCtrl.dismiss(selectedCoords);
            }
          );
        } else {
          const markerOptions = {
            position: new googleMaps.LatLng(
              Number(this.center.lat),
              Number(this.center.lng)
            ),
            map: map,
          };
          const marker = new googleMaps.Marker(markerOptions);
          marker.setTitle('Picked Location');
          marker.setIcon(
            'http://maps.google.com/mapfiles/ms/icons/red-dot.png'
          );
          marker.setMap(map);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }

  private getGoogleMaps(): Promise<any> {
    const win = window as any;
    const googleModule = win.google;
    if (googleModule && googleModule.maps) {
      return Promise.resolve(googleModule.maps);
    }
    return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.src =
        'https://maps.googleapis.com/maps/api/js?key=' +
        environment.googleMapsAPIKey;
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);
      script.onload = () => {
        const loadedGoogleModule = win.google;
        if (loadedGoogleModule && loadedGoogleModule.maps) {
          resolve(loadedGoogleModule.maps);
        } else {
          reject('Google maps SDK not available.');
        }
      };
    });
  }

  ngOnDestroy() {
    if (this.clickListener) {
      this.googleMaps.event.removeListener(this.clickListener);
    }
  }
}
