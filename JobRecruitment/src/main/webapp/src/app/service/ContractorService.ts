import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Contractor} from "../model/Contractor";
import {Observable} from "rxjs";

@Injectable()
export class ContractorService{

  private contractorURL = 'http://localhost:8080/contractor';

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  saveContractor(formData: FormData): Observable<HttpResponse<Contractor>>{
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    headers.set('Accept', 'application/json');
    return this.httpClient.post<Contractor>(this.contractorURL + '/saveContractor',formData,
      {headers: headers, observe: "response"});
  }
}
