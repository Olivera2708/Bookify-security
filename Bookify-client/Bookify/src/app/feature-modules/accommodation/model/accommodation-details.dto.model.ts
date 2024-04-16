import { Address } from "./address.dto.model";
import {ReviewDTO} from "./review.dto.model";
import {Owner} from "../../account/model/owner";

export interface AccommodationDetailsDTO {
  id: number;
  name: string;
  description: string;
  reviews: ReviewDTO[];
  avgRating: number;
  filters: string[];
  address: Address;
  owner: Owner;
  pricePer: string
}
