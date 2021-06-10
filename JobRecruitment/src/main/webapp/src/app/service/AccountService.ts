import {Observable} from "rxjs";
import {User} from "../model/User";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";
import {Book} from "../model/Book";
import {Contractor} from "../model/Contractor";
import {environment} from "../../environments/environment";


@Injectable()
export class AccountService{
  private accountURL = environment.apiBaseUrl + '/user';
  public currentUser: Observable<User>;

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  login(email: string, password: string) : Observable<HttpResponse<User>>{
    return this.httpClient.get<User>( this.accountURL + '/login?email=' + email +"&password=" + password, {observe: 'response'});
  }

  logout(){
    sessionStorage.removeItem("currentUser");
    sessionStorage.removeItem("contractor");
    this.router.navigate([""])
  }

  register(user: User) : Observable<HttpResponse<User>>{
    return this.httpClient.post<User>( this.accountURL + '/register', user, {observe: "response"});
  }

  modify(modifiedUser: User) : Observable<HttpResponse<User>>{
    return this.httpClient.put<User>( this.accountURL + '/modify', modifiedUser, {observe: "response"});
  }

  getJobCandidates(jobId: number): Observable<HttpResponse<User[]>>
  {
    return this.httpClient.get<User[]>(this.accountURL + '/findJobCandidates/'+jobId, {observe: "response"});
  }

  removeUser(id: number): Observable<HttpResponse<void>>
  {
    return this.httpClient.delete<void>(this.accountURL + '/removeUser/' + id, {observe: "response"});
  }

  getAllUsers(): Observable<HttpResponse<User[]>>
  {
    return this.httpClient.get<User[]>(this.accountURL + '/findAllUsers', {observe: "response"});
  }
}
