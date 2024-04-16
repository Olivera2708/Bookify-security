import {Address} from "./address";
export interface AccommodationBasicModel {
  id?: number;
  name: string;
  avgRating: number;
  totalPrice: number;
  priceOne: number;
  imageId: number;
  address: Address;
  pricePer: string;
  type: string;
  description: string;
}
