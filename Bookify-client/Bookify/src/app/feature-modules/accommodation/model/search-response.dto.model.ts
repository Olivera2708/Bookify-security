import {AccommodationBasicModel} from "./accommodation-basic.model";

export interface SearchResponseDTO {
  accommodations: AccommodationBasicModel[];
  results: number;
  minPrice: number;
  maxPrice: number;
}
