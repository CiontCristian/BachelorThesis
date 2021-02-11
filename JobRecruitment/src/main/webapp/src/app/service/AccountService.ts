import {Observable} from "rxjs";
import {User} from "../model/User";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";
import {Book} from "../model/Book";
import {Contractor} from "../model/Contractor";


@Injectable()
export class AccountService{
  private accountURL = 'http://localhost:8080/user';
  private pythonURL = 'http://127.0.0.1:5000/python/books';
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
    this.router.navigate([""])
  }

  register(user: User) : Observable<HttpResponse<User>>{
    console.log(user)
    return this.httpClient.post<User>( this.accountURL + '/register', user, {observe: "response"});
  }

  modifyUserContractor(id: number, contractor: Contractor) : Observable<HttpResponse<User>>{
    return this.httpClient.put<User>(this.accountURL + '/modifyCompany/' + id, contractor, {observe: "response"});
  }

  getAllFromPython() : Observable<Book[]>{
    return this.httpClient.get<Book[]>(this.pythonURL + '/all');
  }

  sendAllToPython(books: Book[]) : Observable<Book[]>{

    return this.httpClient.post<Book[]>(this.pythonURL + '/getData', books);
  }
}
