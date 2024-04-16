import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { AccommodationBasicModel } from "./model/accommodation-basic.model";
import { HttpClient, HttpParams, HttpResponse } from "@angular/common/http";
import { BehaviorSubject, map, Observable } from "rxjs";
import { environment } from "../../../env/env";
import moment from 'moment';
import { AccommodationDTO } from './model/accommodation.dto.model';
import { Accommodation } from './model/accommodation.model';
import { PriceListDTO } from './model/priceList.dto.model';
import { PriceList } from './model/priceList.model';
import { FilterDTO } from "./model/filter.dto.model";
import { SearchResponseDTO } from "./model/search-response.dto.model";
import { AccommodationDetailsDTO } from "./model/accommodation-details.dto.model";
import { ImageFileDTO } from './model/images.dto.model';
import { ReservationRequestDTO } from "./model/reservation-request.dto.model";
import { Reservation } from "./model/reservation.model";
import { AccommodationOwnerDtoModel } from "./model/accommodation.owner.dto.model";
import {ChartsDTO} from "./model/accommodation-charts.dto.model";

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getOwnerAccommodations(ownerId: number | undefined): Observable<AccommodationOwnerDtoModel[]> {
    return this.httpClient.get<AccommodationOwnerDtoModel[]>(environment.apiAccommodation + '/' + ownerId);
  }

  getForSearch(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number): Observable<SearchResponseDTO> {
    return this.httpClient.get<SearchResponseDTO>(environment.apiHost + 'accommodations/' +
      "search?location=" + location +
      "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
      "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
      "&persons=" + persons +
      "&page=" + page +
      "&size=" + size);
  }

  getAccommodationDetails(id: number): Observable<AccommodationDetailsDTO> {
    return this.httpClient.get<AccommodationDetailsDTO>(environment.apiHost + 'accommodations/details/' + id);
  }

  getForFilterAndSort(location: string, dateBegin: Date, dateEnd: Date, persons: number, page: number, size: number, sort: string, filter: FilterDTO): Observable<SearchResponseDTO> {
    return this.httpClient.post<SearchResponseDTO>(environment.apiHost + 'accommodations/' +
      "filter?location=" + location +
      "&begin=" + (moment(dateBegin)).format('DD.MM.YYYY') +
      "&end=" + (moment(dateEnd)).format('DD.MM.YYYY') +
      "&persons=" + persons +
      "&page=" + page +
      "&size=" + size +
      "&sort=" + sort, filter);

  }

  getTotalPrice(accommodationId: number, begin: Date, end: Date, pricePer: string, persons: number) {
    return this.httpClient.get<number>(environment.apiHost + 'accommodations/' +
      "price?id=" + accommodationId +
      "&begin=" + (moment(begin)).format('DD.MM.YYYY') +
      "&end=" + (moment(end)).format('DD.MM.YYYY') +
      "&pricePer=" + pricePer +
      "&persons=" + persons);
  }

  getImage(imageId: number): Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "accommodations/image/" + imageId, { responseType: 'blob' });
  }

  getAccommodationImages(accommodationId: number): Observable<string[]> {
    return this.httpClient.get<string[]>(environment.apiHost + "accommodations/images/" + accommodationId, { responseType: "json" });
  }

  addToFavorites(guestId: number, accommodationId: number): Observable<string> {
    const url = `${environment.apiHost}accommodations/add-to-favorites/${guestId}/${accommodationId}`;
    return this.httpClient.post(url, {}, { responseType: "text" });
  }

  getFavorites(guestId: number): Observable<AccommodationBasicModel[]>{
    return this.httpClient.get<AccommodationBasicModel[]>(environment.apiHost + "accommodations/favorites?guestId=" + guestId);
  }

  checkIfInFavorites(guestId: number, accommodationId: number): Observable<boolean> {
    const url = `${environment.apiHost}accommodations/added-to-favorites/${guestId}/${accommodationId}`;
    return this.httpClient.get<boolean>(url);
  }

  async getCountries(): Promise<string[]> {
    try {
      const response = await fetch('https://restcountries.com/v3.1/all');
      const countriesData = await response.json();
      const countries = countriesData.map((country: any) => country.name.common);
      countries.sort();
      return countries;
    } catch (error) {
      console.error('Error fetching countries:', error);
      return [];
    }
  }

  getAllPriceListItems(accommodationId: number): Observable<PriceList[]> {
    return this.httpClient.get<PriceList[]>(environment.apiAccommodation + '/' + accommodationId + "/getPrice");
  }

  add(ownerId: number, accommodation: AccommodationDTO): Observable<Accommodation> {
    const params = new HttpParams().set('ownerId', ownerId);
    return this.httpClient.post<Accommodation>(environment.apiAccommodation, accommodation, { params });
  }

  modify(accommodation: Accommodation): Observable<number> {
    return this.httpClient.put<number>(environment.apiAccommodation, accommodation);
  }

  getAccommodation(accommodationId: number): Observable<Accommodation> {
    return this.httpClient.get<Accommodation>(environment.apiAccommodation + "/edit/" + accommodationId);
  }

  deletePriceListItem(accommodationId: number, priceListItem: PriceListDTO): Observable<PriceList> {
    return this.httpClient.delete<PriceList>(environment.apiAccommodation + "/price/" + accommodationId, { "body": priceListItem });
  }

  addImages(accommodationId: number, images: File[]) {
    const data: FormData = new FormData();
    images.forEach((file: File) => {
      data.append("images", file);
    })
    return this.httpClient.post<string[]>(environment.apiAccommodation + "/" + accommodationId, data);
  }

  getImages(accommodationId: number): Observable<Uint8Array[]> {
    return this.httpClient.get<Uint8Array[]>(environment.apiAccommodation + "/images/" + accommodationId);
  }

  getImagesDTO(accommodationId: number): Observable<ImageFileDTO[]> {
    return this.httpClient.get<ImageFileDTO[]>(environment.apiAccommodation + "/images/files/" + accommodationId);
  }

  addPriceList(accommodationId: number, priceList: PriceListDTO) {
    return this.httpClient.post<PriceListDTO>(environment.apiAccommodation + "/" + accommodationId + "/addPrice", priceList);
  }

  createReservationRequest(reservation: ReservationRequestDTO, accommodationId: number, guestId: number): Observable<Reservation> {
    const params = new HttpParams().set('accommodationId', accommodationId).set('guestId', guestId);
    return this.httpClient.post<Reservation>(environment.apiHost + "reservations/create", reservation, { params });
  }

  deleteImage(imageId: number): Observable<number> {
    return this.httpClient.delete<number>(environment.apiAccommodation + "/images/" + imageId);
  }

  getOverallCharts(ownerId: number, startDate: string, endDate: string): Observable<ChartsDTO[]> {
    return this.httpClient.get<ChartsDTO[]>(environment.apiHost + "accommodations/overall-charts?ownerId=" + ownerId +
      "&begin=" + startDate +
      "&end=" + endDate);
  }

  getNamesForAccommodations(ownerId: number): Observable<{ [key: number]: string }> {
    return this.httpClient.get<{ [key: number]: string }>(environment.apiHost + "accommodations/charts-accommodations?ownerId=" + ownerId);
  }

  getAccommodationCharts(ownerId: number, accommodationId: number, year: number): Observable<ChartsDTO[]> {
    return this.httpClient.get<ChartsDTO[]>(environment.apiHost + "accommodations/accommodation-charts?ownerId=" + ownerId + "&accommodationId=" + accommodationId + "&year=" + year);
  }


  generatePdfReportForOverall(ownerId: number, begin: string, end:string): Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "accommodations/download-reports-overall?ownerId=" + ownerId +
      "&begin=" + begin + "&end=" + end, { responseType: 'blob' });
  }

  generatePdfReportForAccommodation(ownerId: number, accommodationId: number, year: number): Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "accommodations/download-reports-accommodation?ownerId=" + ownerId +
      "&accommodationId=" + accommodationId + "&year=" + year, { responseType: 'blob' });
  }

  getTopAccommodations(): Observable<AccommodationBasicModel[]> {
    return this.httpClient.get<AccommodationBasicModel[]>(environment.apiHost + 'accommodations/top-accommodations?results=2');
  }
}
