import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";


@Injectable()
export class StatisticsService{
  private statisticURL = environment.apiBaseUrl + '/statistic';

  constructor(
    private httpClient: HttpClient,
  ) {}

  getCompaniesWithNumberOfOffers(): Observable<HttpResponse<any[]>>
  {
    return this.httpClient.get<any>(this.statisticURL + "/companiesWithNumberOfOffers" ,{observe: "response"});
  }

  mostLikedJobs(): Observable<HttpResponse<any[]>>
  {
    return this.httpClient.get<any>(this.statisticURL + "/mostLikedJobs" ,{observe: "response"});
  }

  mostAppliedJobsForContractor(id: number): Observable<HttpResponse<any[]>>
  {
    return this.httpClient.get<any>(this.statisticURL + "/mostAppliedJobsForContractor/"+id ,{observe: "response"});
  }
}
