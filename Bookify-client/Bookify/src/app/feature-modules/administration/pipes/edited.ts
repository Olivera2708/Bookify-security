import {Pipe} from "@angular/core";
import {AccommodationRequests} from "../model/accommodation.requests";

@Pipe({
    name: 'edited',
})
export class EditedPipe {
    transform(requests: AccommodationRequests[]): AccommodationRequests[] {
        if (requests) {
            return requests.filter(request => {
                return request.statusRequest === 'EDITED';
            })
        }
        return [];
    }
}
