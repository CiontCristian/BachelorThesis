import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AccountService} from "../service/AccountService";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../model/User";
import {Permission} from "../model/Permission";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registeredUser : User = null

  emailForm = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  passwordForm = new FormControl('', [Validators.required])
  usernameForm = new FormControl('', [Validators.required])
  firstNameForm = new FormControl('', [Validators.required])
  lastNameForm = new FormControl('', [Validators.required])
  birthDateForm = new FormControl('', [Validators.required])
  gender : string = ""
  telephoneForm = new FormControl('', [Validators.required])
  hidePassword = true

  constructor(private accountService: AccountService,
              private router: Router,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  getEmailErrorMessage() {
    if (this.emailForm.hasError('required')) {
      return 'Email is required!';
    }

    return this.emailForm.hasError('email') ? 'Not a valid email' : '';
  }

  register() {
    let permission : Permission = new Permission(0, true, false)

    let newUser : User = new User(0, this.usernameForm.value, this.passwordForm.value, this.emailForm.value,
      this.firstNameForm.value, this.lastNameForm.value, new Date(this.birthDateForm.value), this.telephoneForm.value,
      this.gender, permission)

    console.log(newUser)
    this.accountService.register(newUser).subscribe( response => {
        this.registeredUser = response.body;
        this.snackBar.open("Registration Successful", "Go to login!")
        this.router.navigate(["login"]);
    },
      error => {
        console.log(error);
        this.snackBar.open("Email or username already taken", "Registration Failed!")
      });
  }
}
