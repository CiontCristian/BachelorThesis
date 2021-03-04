import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Contractor} from "../model/Contractor";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable()
export class ContractorService{

  private contractorURL = environment.apiBaseUrl + '/contractor';

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

  findContractorForUser(id: number): Observable<HttpResponse<Contractor>>{
    return this.httpClient.get<Contractor>(this.contractorURL + '/findContractorForUser/' + id, {observe: "response"});
  }

  modifyContractor(formData: FormData): Observable<HttpResponse<Contractor>>{
    return this.httpClient.put<Contractor>(this.contractorURL + '/modifyContractor',formData,
      {observe: "response"});
  }
}
