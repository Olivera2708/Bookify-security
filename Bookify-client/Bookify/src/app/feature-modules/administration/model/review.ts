export interface Review {
  id?: number,
  date?: string,
  comment?: string,
  rate?: number,
  accepted?: boolean,
  reported?: boolean,
  guest: {
    id?:number,
    firstName?:string,
    lastName?:string,
    email?:string,
    imageId?: number
  }
  imageId?: number,
  reviewType: string
}
