import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Job} from "../model/Job";

@Injectable()
export class JobService{
  private jobURL = 'http://localhost:8080/job';

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  getAllJobs(pageIndex: number, pageSize: number): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.get<Job[]>(this.jobURL + "/findAllJobs?pageIndex="+pageIndex+"&pageSize="+pageSize,
      {observe: "response"});
  }
}
