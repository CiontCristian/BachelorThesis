import { Component, OnInit } from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {AccountService} from "../service/AccountService";
import {Router} from "@angular/router";
import {HomeComponent} from "../home/home.component";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ToolbarComponent} from "../toolbar/toolbar.component";
import {Book} from "../model/Book";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  emailForm = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  passwordForm = new FormControl('', [Validators.required])
  hidePassword = true;
  private currentUser : User;
  arr: Book[] = [];

  constructor(private accountService: AccountService,
              private router: Router,
              private toolbarComponent: ToolbarComponent,
              private snackBar : MatSnackBar
              ) { }

  ngOnInit(): void {
  }

  getEmailErrorMessage() {
    if (this.emailForm.hasError('required')) {
      return 'Email is required!';
    }

    return this.emailForm.hasError('email') ? 'Not a valid email' : '';
  }

  login(): void {
      this.accountService.login(this.emailForm.value, this.passwordForm.value)
        .subscribe(response => {

          this.currentUser = response.body;

          this.snackBar.open("Login successful!", "Welcome");
          sessionStorage.setItem("currentUser", JSON.stringify(this.currentUser));
          this.toolbarComponent.ngOnInit();
          this.router.navigate([""]);
        },
          error => {
            console.log(error);
            this.snackBar.open("Invalid credentials!", "Retry");
            this.currentUser = null;
          })
  }

  getPythonBooks() {
    this.accountService.getAllFromPython().subscribe(
      data => console.log(data)
    )
  }

  sendPythonBooks(){
    let book : Book = new Book(1, "Cal", "Sal", "calut cal", 120);
    console.log(book);

    this.arr.push(book);

    this.accountService.sendAllToPython(this.arr).subscribe();
  }
}
