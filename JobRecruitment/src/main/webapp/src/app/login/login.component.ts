import { Component, OnInit } from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {AccountService} from "../service/AccountService";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ToolbarComponent} from "../toolbar/toolbar.component";
import {Book} from "../model/Book";
import {ContractorService} from "../service/ContractorService";

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

  constructor(private accountService: AccountService,
              private contractorService: ContractorService,
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

          this.snackBar.open("Login successful!", "Welcome", {duration: 3000});
          sessionStorage.setItem("currentUser", JSON.stringify(this.currentUser));
          if(this.currentUser.permission.isCompany){
            this.contractorService.findContractorForUser(this.currentUser.id).subscribe(
              response =>{
                  sessionStorage.setItem("contractor", JSON.stringify(response.body));
              },
              error => console.log("Contractor for user not yet set!")
            )
          }
          this.toolbarComponent.ngOnInit();
          this.router.navigate([""]);
        },
          error => {
            console.log(error);
            this.snackBar.open(error.error, "Retry", {duration: 3000});
            this.currentUser = null;
          })
  }
}
