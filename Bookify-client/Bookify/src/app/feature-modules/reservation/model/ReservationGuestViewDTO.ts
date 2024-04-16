import {UserReservationDTO} from "./UserReservationDTO";

export interface ReservationGuestViewDTO {
  id: number;
  created: Date;
  start: string;
  end: string;
  cancellationDate: string;
  guestNumber: number;
  price: number;
  status: string;
  user: UserReservationDTO;
  accommodationId: number;
  accommodationName: string;
  avgRating: number;
  imageId: number;
}
