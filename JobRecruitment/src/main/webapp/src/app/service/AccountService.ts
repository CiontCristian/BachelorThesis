import {Observable} from "rxjs";
import {User} from "../model/User";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";


@Injectable()
export class AccountService{
  private accountURL = 'http://localhost:8080/user';
  public currentUser: Observable<User>;

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  login(email: string, password: string) : Observable<User>{
    return this.httpClient.post<User>( this.accountURL + '/login', [email, password]);
  }

  logout(){
    sessionStorage.removeItem("currentUser");
    this.router.navigate([""])
  }

  register(user: User) : Observable<User>{
    console.log(user)
    return this.httpClient.post<User>( this.accountURL + '/register', user)
  }
}
