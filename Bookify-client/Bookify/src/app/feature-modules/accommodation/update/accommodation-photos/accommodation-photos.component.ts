import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ImagesDTO } from '../../model/images';
import { AccommodationService } from '../../accommodation.service';
@Component({
  selector: 'app-accommodation-photos',
  templateUrl: './accommodation-photos.component.html',
  styleUrl: './accommodation-photos.component.css'
})

export class AccommodationPhotosComponent implements OnChanges {
  @Output() photosChanged = new EventEmitter<ImagesDTO[]>();
  @Input() images: ImagesDTO[];

  selectedImagesObject: ImagesDTO[] = [];

  constructor(private accommodationService: AccommodationService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (this.images) {
      this.selectedImagesObject = this.images;
    }
  }

  onFilesSelected(event: any): void {
    const files: FileList | null = event.target.files;

    if (files) {
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          if (e.target) {
            const imageDataURL = e.target.result as string;
            const imageDTO: ImagesDTO = {
              url: imageDataURL,
              file: files[i],
              id: null
            }
            this.selectedImagesObject.push(imageDTO);
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }
    this.photosChanged.emit(this.selectedImagesObject);
  }

  removeImage(image: ImagesDTO): void {
    this.selectedImagesObject = this.selectedImagesObject.filter(img => img !== image);
    this.photosChanged.emit(this.selectedImagesObject);
    if(image.id){
      console.log(image.id);
      this.accommodationService.deleteImage(image.id).subscribe();
    }
  }

}
