import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccommodationRequests} from "./model/accommodation.requests";
import {environment} from "../../../env/env";
import {User} from "./model/user";
import {ReportedUser} from "./model/reported.user";
import {Review} from "./model/review";

@Injectable({
  providedIn: 'root'
})
export class AdministrationService {

  constructor(private httpClient: HttpClient) {
  }


  getAccommodationRequests(): Observable<AccommodationRequests[]> {
    return this.httpClient.get<AccommodationRequests[]>(environment.apiAccommodation + "/requests")
  }

  rejectAccommodationRequest(accommodationId: number): Observable<string> {
    return this.httpClient.put(environment.apiAccommodation + "/reject/" + accommodationId, {}, {responseType: "text"});
  }

  approveAccommodationRequest(accommodationId: number): Observable<string> {
    return this.httpClient.put(environment.apiAccommodation + "/approve/" + accommodationId, {}, {responseType: "text"});
  }

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(environment.apiUser);
  }

  blockUser(userId: undefined | number): Observable<User> {
    return this.httpClient.put<User>(environment.apiUser + "/" + userId + "/block-user", {});
  }

  unblockUser(userId: undefined | number): Observable<User> {
    return this.httpClient.put<User>(environment.apiUser + "/" + userId + "/unblock-user", {});
  }
  getAllReports(): Observable<ReportedUser[]> {
    return this.httpClient.get<ReportedUser[]>(environment.apiUser + "/reported");
  }

  getAllCreatedReviews(): Observable<Review[]> {
    return this.httpClient.get<Review[]>(environment.apiReview + "/created");
  }

  getAllReportedReviews(): Observable<Review[]>{
    return this.httpClient.get<Review[]>(environment.apiReview + "/reported");
  }

  acceptReview(reviewId: number | undefined): Observable<Review> {
    return this.httpClient.put<Review>(environment.apiReview + "/accept/" + reviewId,{});
  }

  declineReview(reviewId: number | undefined): Observable<Review> {
    return this.httpClient.put<Review>(environment.apiReview + "/decline/" + reviewId,{});
  }

  ignoreReview(reviewId: number | undefined): Observable<Review> {
    return this.httpClient.put<Review>(environment.apiReview + "/ignore/" + reviewId,{});
  }

  removeReview(reviewId: number | undefined): Observable<Review> {
    return this.httpClient.delete<Review>(environment.apiReview + "/remove/" + reviewId);
  }
}
