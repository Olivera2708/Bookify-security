import { Address } from "./address.dto.model";

export interface Accommodation {
    id: number;
    name: string;
    description: string;
    minGuest: number;
    maxGuest: number;
    cancellationDeadline: number;
    manual: boolean;
    filters: string[];
    accommodationType: string;
    pricePer: string;
    address: Address;
}
