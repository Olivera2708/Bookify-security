import {UserReservationDTO} from "./UserReservationDTO";

export interface ReservationDTO {
  id: number;
  created: Date;
  start: string;
  end: string;
  guestNumber: number;
  price: number;
  status: string;
  user: UserReservationDTO;
  accommodationId: number;
  accommodationName: string;
  avgRating: number;
  imageId: number;
}
