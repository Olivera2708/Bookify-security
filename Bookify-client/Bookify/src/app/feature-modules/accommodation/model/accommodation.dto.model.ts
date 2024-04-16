    import { Address } from "./address.dto.model";

    export interface AccommodationDTO {
        name: string;
        description: string;
        minGuest: number;
        maxGuest: number;
        cancellationDeadline: number;
        manual: boolean;
        filters: string[];
        accommodationType: string | null;
        pricePer: string | null;
        address: Address;
    }
    