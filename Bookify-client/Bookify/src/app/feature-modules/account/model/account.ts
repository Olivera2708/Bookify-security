import {Address} from "./address";

export interface Account {
  id?: number,
  email?: string,
  firstName?: string,
  lastName?: string,
  blocked?: boolean,
  phone?: string,
  address?: Address,
  imageId?: number
}
