import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Job} from "../model/Job";
import {Preference} from "../model/Preference";
import {environment} from "../../environments/environment";

@Injectable()
export class JobService{
  private jobURL = environment.apiBaseUrl + '/job';

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  getAllJobs(pageIndex: number, pageSize: number): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.get<Job[]>(this.jobURL + "/findAllJobs?pageIndex="+pageIndex+"&pageSize="+pageSize,
      {observe: "response"});
  }

  saveJob(job: Job): Observable<HttpResponse<Job>>{
    return this.httpClient.post<Job>(this.jobURL + "/saveJob", job, {observe: "response"});
  }

  getJob(id: number): Observable<HttpResponse<Job>>
  {
    return this.httpClient.get<Job>(this.jobURL + "/findJobById/" + id,
      {observe: "response"});
  }

  savePreference(preference: Preference): Observable<HttpResponse<Preference>>
  {
    return this.httpClient.post<Preference>(this.jobURL + "/savePreference", preference, {observe: "response"});
  }

  findJobsForContractor(id: number): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.get<Job[]>(this.jobURL + "/findJobsForContractor/" + id,
      {observe: "response"});
  }

}
