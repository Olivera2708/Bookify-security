// search-results.service.ts
import { Injectable } from '@angular/core';
import {SearchResponseDTO} from "./model/search-response.dto.model";
import {FilterDTO} from "./model/filter.dto.model";
import {AccommodationBasicModel} from "./model/accommodation-basic.model";

@Injectable({
  providedIn: 'root',
})
export class SearchResultsService {
  private _accommodations: AccommodationBasicModel[] | undefined;
  private _filters: FilterDTO;
  private _minPrice: number;
  private _maxPrice: number;
  private _selectMinPrice: number;
  private _selectMaxPrice: number;
  private _results: number;
  private _currentPage: number;
  private _pageSize: number;

  get accommodations(): AccommodationBasicModel[] | undefined{
    return this._accommodations;
  }

  set accommodations(value: AccommodationBasicModel[]| undefined) {
    this._accommodations = value;
  }

  get filters(): FilterDTO {
    return this._filters;
  }

  set filters(value: FilterDTO) {
    this._filters = value;
  }

  get minPrice(): number {
    return this._minPrice;
  }

  set minPrice(value: number) {
    this._minPrice = value;
  }

  get maxPrice(): number {
    return this._maxPrice;
  }

  set maxPrice(value: number) {
    this._maxPrice = value;
  }

  get selectMinPrice(): number {
    return this._selectMinPrice;
  }

  set selectMinPrice(value: number) {
    this._selectMinPrice = value;
  }

  get selectMaxPrice(): number {
    return this._selectMaxPrice;
  }

  set selectMaxPrice(value: number) {
    this._selectMaxPrice = value;
  }

  get results(): number {
    return this._results;
  }

  set results(value: number) {
    this._results = value;
  }

  get currentPage(): number {
    return this._currentPage;
  }

  set currentPage(value: number) {
    this._currentPage = value;
  }

  get pageSize(): number {
    return this._pageSize;
  }

  set pageSize(value: number) {
    this._pageSize = value;
  }
}
