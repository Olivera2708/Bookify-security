import {GuestDTO} from "../../account/model/guest.dto";
import {Accommodation} from "./accommodation.model";

export interface Reservation {
  created: Date,
  start: Date,
  end: Date,
  guestNumber: number,
  guest: GuestDTO,
  accommodation: Accommodation,
  status: string
}
