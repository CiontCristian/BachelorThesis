import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../service/AccountService";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../model/User";
import {Permission} from "../model/Permission";
import {Background} from "../model/Background";
import {Location} from "../model/Location";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registeredUser : User = null

  generalFormGroup: FormGroup;
  locationFormGroup: FormGroup;
  backgroundFormGroup: FormGroup;

  hidePassword = true

  constructor(private accountService: AccountService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generalFormGroup = this.formBuilder.group({
      emailForm : new FormControl('', [
        Validators.required,
        Validators.email
      ]), passwordForm: new FormControl('', [Validators.required]),usernameForm: new FormControl('', [Validators.required]),
      firstNameForm: new FormControl('', [Validators.required]), lastNameForm: new FormControl('', [Validators.required]),
      birthDateForm: new FormControl('', [Validators.required]),telephoneForm: new FormControl('', [Validators.required]),
      genderForm : new FormControl('', [Validators.required])
    });
    this.locationFormGroup = this.formBuilder.group({
      addressForm: new FormControl(''), cityForm: new FormControl(''),
      countryForm: new FormControl('')
    });
    this.backgroundFormGroup = this.formBuilder.group({
      formalEducationForm: new FormControl('', [Validators.required]), experienceForm: new FormControl('', [Validators.required]),
      permissionForm: new FormControl('', Validators.required)
    });
  }

  getEmailErrorMessage() {
    if (this.generalFormGroup.get('emailForm').hasError('required')) {
      return 'Email is required!';
    }

    return this.generalFormGroup.get('emailForm').hasError('email') ? 'Not a valid email' : '';
  }

  register() {
    const value = this.backgroundFormGroup.get('permissionForm').value === 'client';
    let permission : Permission = new Permission(null, value === true, value !== true, false);
    let background: Background = new Background(null, this.backgroundFormGroup.get('formalEducationForm').value,
      this.backgroundFormGroup.get('experienceForm').value);
    let location: Location = new Location(null, this.locationFormGroup.get('addressForm').value, this.locationFormGroup.get('cityForm').value,
      this.locationFormGroup.get('countryForm').value);

    let newUser : User = new User(null, this.generalFormGroup.get('usernameForm').value, this.generalFormGroup.get('passwordForm').value, this.generalFormGroup.get('emailForm').value,
      this.generalFormGroup.get('firstNameForm').value, this.generalFormGroup.get('lastNameForm').value,
      new Date(this.generalFormGroup.get('birthDateForm').value), this.generalFormGroup.get('telephoneForm').value, this.generalFormGroup.get('genderForm').value,
      background, permission, location, null);

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
