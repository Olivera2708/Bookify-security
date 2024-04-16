import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {FilterDTO} from "../model/filter.dto.model";
import {FilterComponent} from "../../../layout/filter/filter.component";
import {SearchComponent} from "../../../layout/search/search.component";
import {AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, ViewChild} from "@angular/core";
import {SearchResultsService} from "../SearchResultService";
import {filter} from "rxjs";

@Component({
  selector: 'app-results-page',
  templateUrl: './results-page.component.html',
  styleUrl: './results-page.component.css',
  changeDetection: ChangeDetectionStrategy.Default
})
export class ResultsPageComponent implements OnInit, AfterViewInit{
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(FilterComponent) filterComponent: FilterComponent;
  @ViewChild(SearchComponent) searchComponent: SearchComponent;
  accommodationModels: AccommodationBasicModel[]
  search: string;
  persons: number;
  dateBegin: Date;
  dateEnd: Date;
  dateBeginS: string;
  dateEndS: string;
  currentPage = 1;
  pageSize = 5;
  allResults: number;
  sort: string = "";
  loaded: boolean = false;
  filter: FilterDTO = {maxPrice: -1, minPrice: -1, filters: [], types: ["HOTEL", "APARTMENT", "ROOM"]}

  constructor(private accommodationService: AccommodationService, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef, private searchResultsService: SearchResultsService) {}

  onSortChange() {
    this.currentPage = 1;
    this.paginator.pageIndex = 0;
    this.getSortAndFilterResults();
  }

  getSortAndFilterResults(){
    this.accommodationService.getForFilterAndSort(this.search, this.dateBegin, this.dateEnd, this.persons, this.currentPage-1, this.pageSize, this.sort, this.filter).subscribe({
      next: (data) => {
        this.accommodationModels = data.accommodations;
        this.allResults = data.results;
        this.saveToCache();
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }

  getResults() {
    this.accommodationService.getForSearch(this.search, this.dateBegin, this.dateEnd, this.persons, this.currentPage-1, this.pageSize).subscribe({
      next: (data) => {
        this.accommodationModels = data.accommodations;
        this.allResults = data.results;
        this.filterComponent.minPossiblePrice = data.minPrice;
        this.filterComponent.minPrice = data.minPrice;
        this.filterComponent.maxPossiblePrice = data.maxPrice + 1;
        this.filterComponent.maxPrice = data.maxPrice;
        this.saveToCache();
      },
      error: (_) => {
        console.log("Error occurred!");
      }
    });
  }

  onPageChange(event: PageEvent) {
    if (this.pageSize != event.pageSize) {
      this.pageSize = event.pageSize;
      this.paginator.pageIndex = 0;
    }
    if (this.currentPage != event.pageIndex + 1) {
      this.currentPage = event.pageIndex + 1;

      window.scrollTo({
        top: 1000,
        behavior: 'smooth'
      });
    }
    const array: Array<string> = ['Name', 'Lowest', 'Highest'];
    if (array.indexOf(this.sort) != -1 || this.filter.maxPrice != -1 || this.filter.filters.length != 0 || this.filter.types.length != 3)
      this.getSortAndFilterResults();
    else
      this.getResults();
  }

  ngOnInit(): void {
    if (this.searchResultsService.accommodations === undefined) {
      this.loaded = false;
      this.search = <string>this.route.snapshot.params['search'];
      this.persons = Number(this.route.snapshot.params['persons']);
      this.dateBeginS = <string>this.route.snapshot.params['begin'];
      this.dateEndS = <string>this.route.snapshot.params['end'];
      this.dateBegin = new Date(Date.parse(this.dateBeginS));
      this.dateEnd = new Date(Date.parse(this.dateEndS));
      this.getResults();
    }
    else {
      this.accommodationModels = this.searchResultsService.accommodations;
      this.loaded = true;
      this.allResults = this.searchResultsService.results;
      this.currentPage = this.searchResultsService.currentPage;
      this.pageSize = this.searchResultsService.pageSize;
      this.LoadSearchBar();
    }
  }

  ngAfterViewInit() {
    this.searchComponent.persons = this.persons;
    this.searchComponent.search = this.search;
    this.searchComponent.dateComponent.setDate(this.dateBeginS.split("-")[1] + "." + this.dateBeginS.split("-")[0] + "." + this.dateBeginS.split("-")[2],
                                               this.dateEndS.split("-")[1] + "." + this.dateEndS.split("-")[0] + "." + this.dateEndS.split("-")[2])


    if (this.loaded) {
      this.paginator.pageIndex = this.currentPage-1;
      this.LoadFilter();
    }

    this.cdr.detectChanges();
  }

  filterPress(filter: FilterDTO){
    this.currentPage = 1;
    this.paginator.pageIndex = 0;
    this.filter = filter;
    this.getSortAndFilterResults();
  }

  searchPress(values: { search: string; persons: number, dateBegin: string, dateEnd: string}): void {
    this.persons = values.persons;
    this.search = values.search;
    this.dateBegin = new Date(Date.parse(values.dateBegin));
    this.dateEnd = new Date(Date.parse(values.dateEnd));

    this.paginator.pageIndex = 0;
    this.currentPage = 1;

    this.filter = {maxPrice: -1, minPrice: -1, filters: [], types: ["HOTEL", "APARTMENT", "ROOM"]}
    this.filterComponent.resetFilter();
    this.sort = "";

    this.getResults();

    this.router.navigate(['/results', {"search": values.search, "persons": values.persons, "begin": values.dateBegin, "end": values.dateEnd}]);
  }

  saveToCache(){
    this.searchResultsService.pageSize = this.pageSize;
    this.searchResultsService.currentPage = this.currentPage;
    this.searchResultsService.results = this.allResults;
    this.searchResultsService.accommodations = this.accommodationModels;
    this.searchResultsService.selectMaxPrice = this.filterComponent.maxPrice;
    this.searchResultsService.selectMinPrice = this.filterComponent.minPrice;
    this.searchResultsService.maxPrice = this.filterComponent.maxPossiblePrice;
    this.searchResultsService.minPrice = this.filterComponent.minPossiblePrice;
    this.searchResultsService.filters = {filters: this.filter.filters, types: this.filter.types, maxPrice: this.filter.maxPrice, minPrice: this.filter.minPrice};
  }

  LoadSearchBar(){
    this.search = <string>this.route.snapshot.params['search'];
    this.persons = Number(this.route.snapshot.params['persons']);
    this.dateBeginS = <string>this.route.snapshot.params['begin'];
    this.dateEndS = <string>this.route.snapshot.params['end'];
    this.dateBegin = new Date(Date.parse(this.dateBeginS));
    this.dateEnd = new Date(Date.parse(this.dateEndS));
  }

  LoadFilter(){
    this.filterComponent.loadFilter(this.searchResultsService.selectMinPrice, this.searchResultsService.selectMaxPrice, this.searchResultsService.minPrice, this.searchResultsService.maxPrice, this.searchResultsService.filters);
  }
}
