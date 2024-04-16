import { AfterViewInit, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { MapService } from "./map.service";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements AfterViewInit, OnChanges {
  @Output() locationClick = new EventEmitter<string[]>();
  @Input() create: boolean = false;
  @Input() locationAddress: string = "";

  clickedMap: boolean = false;
  private map: any;
  L: any;
  marker: any;

  constructor(private mapService: MapService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.locationAddress !== "" && !this.clickedMap) {
      this.search(this.locationAddress);
    }
    this.clickedMap = false;
  }

  private initMap(L: any): void {
    this.map = L.map('map', {
      center: [45.2396, 19.8227],
      attributionControl: false,
      zoom: 13,
    });

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 3,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );
    tiles.addTo(this.map);

    if (this.create) {
      this.registerOnClick();
    } else {
      this.search(L);
    }
  }

  setCenter(coordinates: any) {
    if (this.map) {
      this.map.setView(coordinates, this.map.getZoom());
    }
  }

  registerOnClick(): void {
    this.map.on('click', (e: any) => {
      const coord = e.latlng;
      const lat = coord.lat;
      const lng = coord.lng;
      this.clickedMap = true;

      if (this.marker) {
        this.map.removeLayer(this.marker);
      }
      this.mapService.reverseSearch(lat, lng).subscribe((res) => {
        this.locationClick.emit([
          res.address.country || "",
          res.address.city || res.address.town || res.address.village || "",
          res.address.road || "",
          res.address.postcode || "",
          res.address.house_number || ""
        ]);
      });
      console.log(
        'You clicked the map at latitude: ' + lat + ' and longitude: ' + lng
      );
      this.marker = new this.L.Marker([lat, lng]).addTo(this.map);
    });
  }

  search(location: string): void {
    this.mapService.search(location).subscribe({
      next: (result) => {
        if (this.marker) {
          this.map.removeLayer(this.marker);
        }
        if(result.length>0){
          if(this.L){
            this.marker = this.L.marker([result[0].lat, result[0].lon])
              .addTo(this.map)
              .openPopup();
            this.setCenter([result[0].lat, result[0].lon]);
          }
        }else{
          console.log("GRESKA");
        }
      },
      error: () => { },
    });
  }

  ngAfterViewInit(): void {
    if (typeof window !== 'undefined' && typeof document !== 'undefined') {
      import('leaflet').then((L) => {
        this.L = L;
        L.Marker.prototype.options.icon = L.icon({
          iconUrl: 'https://unpkg.com/leaflet@1.6.0/dist/images/marker-icon.png'
        });
        this.initMap(L);
      })
    }
  }
}
