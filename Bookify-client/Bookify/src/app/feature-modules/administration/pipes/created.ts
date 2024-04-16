import {Pipe} from "@angular/core";
import {AccommodationRequests} from "../model/accommodation.requests";

@Pipe({
    name: 'created',
})
export class CreatedPipe {
    transform(requests: AccommodationRequests[]): AccommodationRequests[] {
        if(requests) {
            return requests.filter(request => {
                return request.statusRequest === 'CREATED';
            })
        }
        return [];
    }
}
