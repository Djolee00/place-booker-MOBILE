import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { Capacitor } from '@capacitor/core';
import { Platform } from '@ionic/angular';

@Component({
  selector: 'app-image-picker',
  templateUrl: './image-picker.component.html',
  styleUrls: ['./image-picker.component.scss'],
})
export class ImagePickerComponent implements OnInit {
  @ViewChild('filePicker') filePicker: ElementRef<HTMLInputElement>;

  @Output()
  imagePick = new EventEmitter<string | File>();

  @Input() showPreview = false;

  selectedImage: string;
  usePicker = false;

  constructor(private platform: Platform) {}

  ngOnInit() {
    console.log('Mobile:', this.platform.is('mobile'));
    console.log('Hybrid:', this.platform.is('hybrid'));
    console.log('iOS:', this.platform.is('ios'));
    console.log('Android:', this.platform.is('android'));
    console.log('Desktop:', this.platform.is('desktop'));
    if (
      (this.platform.is('mobile') && !this.platform.is('hybrid')) ||
      (this.platform.is('desktop') &&
        !this.platform.is('android') &&
        !this.platform.is('ios'))
    ) {
      this.usePicker = true;
    }
  }

  onPickImage() {
    if (!Capacitor.isPluginAvailable('Camera')) {
      this.filePicker.nativeElement.click();
      return;
    }

    Camera.getPhoto({
      quality: 50,
      source: CameraSource.Prompt, // we are asked whether we want
      //to use camera or image gallery
      correctOrientation: true, //Whether to automatically rotate the image "up" to correct for orientation in portrait mode
      height: 320,
      width: 600,
      resultType: CameraResultType.DataUrl,
    })
      .then((image) => {
        this.selectedImage = image.dataUrl!;
        this.imagePick.emit(image.dataUrl!);
      })
      .catch((error) => {
        console.log(error);
        if (this.usePicker) {
          this.filePicker.nativeElement.click();
        }
        return false;
      });
  }

  onFileChosen(event: Event) {
    const pickedFile = (event.target as HTMLInputElement)?.files?.[0];
    if (!pickedFile) {
      return;
    }
    const fr = new FileReader();
    fr.onload = () => {
      const dataUrl = fr.result?.toString();
      this.selectedImage = dataUrl!;
      this.imagePick.emit(pickedFile);
    };
    fr.readAsDataURL(pickedFile);
  }
}
