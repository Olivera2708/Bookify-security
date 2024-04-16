import {Address} from "./address";

export interface AccommodationOwnerDtoModel{
  id: number,
  name: string,
  accommodationType: string,
  avgRating: number,
  statusRequest: string,
  imageId: number
  address: Address;
}
