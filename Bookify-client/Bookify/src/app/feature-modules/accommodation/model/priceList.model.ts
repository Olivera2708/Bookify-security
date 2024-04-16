export interface PriceList {
    id: number,
    startDate: Date;
    endDate: Date;
    price: number;
    formattedStartDate?: string;
    formattedEndDate?: string;
  }
  