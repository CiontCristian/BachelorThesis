import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../service/AccountService";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../model/User";
import {Permission} from "../model/Permission";
import {Background} from "../model/Background";
import {Location} from "../model/Location";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {JobService} from "../service/JobService";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {displayDefaultIndicatorType: false}
  }]
})
export class RegisterComponent implements OnInit {
  registeredUser : User = null

  generalFormGroup: FormGroup;
  backgroundFormGroup: FormGroup;
  latitude: number;
  longitude: number;
  latCluj = 46.74898191760513;
  lngCluj = 23.629722860492784;
  locationChosen: boolean = false;

  availableTechs: string[] = null;
  availableDevTypes: string[] = null;
  jobTypes: string[] = ["part-time", "full-time", "internship"];
  remote: boolean = false;
  experienceLevels: string[] = ["entry", "junior", "middle", "senior", "lead", "manager"];

  hidePassword = true

  constructor(private accountService: AccountService,
              private jobService: JobService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generalFormGroup = this.formBuilder.group({
      emailForm : new FormControl('', [
        Validators.required,
        Validators.email
      ]), passwordForm: new FormControl('', [Validators.required,Validators.minLength(4)]),
      firstNameForm: new FormControl('', [Validators.required]), lastNameForm: new FormControl('', [Validators.required]),
      birthDateForm: new FormControl('', [Validators.required]),
      genderForm : new FormControl('M', [Validators.required])
    });

    this.backgroundFormGroup = this.formBuilder.group({
      techsForm: new FormControl(''), experienceForm: new FormControl(''),
      permissionForm: new FormControl('', Validators.required), jobTypeForm: new FormControl(''),
      devTypeForm: new FormControl(''),remoteForm: new FormControl(false)
    });

    this.jobService.getAvailableTechs()
      .subscribe(response => {this.availableTechs = response.body},
        error => console.log(error.error))
    this.jobService.getAvailableDevTypes()
      .subscribe(response => {this.availableDevTypes = response.body},
        error => console.log(error.error))
  }

  getEmailErrorMessage() {
    if (this.generalFormGroup.get('emailForm').hasError('required')) {
      return 'Email is required!';
    }

    return this.generalFormGroup.get('emailForm').hasError('email') ? 'Not a valid email' : '';
  }

  getPasswordErrorMessage(){
    if (this.generalFormGroup.get('passwordForm').hasError('required')) {
      return 'Password is required!';
    }

    return this.generalFormGroup.get('passwordForm').hasError('minlength') ? 'Password too short!' : '';
  }

  register() {
    const value = this.backgroundFormGroup.get('permissionForm').value === 'client';
    let permission : Permission = new Permission(null, value === true, value !== true, false);
    let location: Location = new Location(null, this.latitude, this.longitude);

    let background: Background = new Background(null, this.backgroundFormGroup.get('techsForm').value.toString(),
      this.backgroundFormGroup.get('experienceForm').value, this.backgroundFormGroup.get('jobTypeForm').value.toString(),
      this.backgroundFormGroup.get('devTypeForm').value.toString(), this.backgroundFormGroup.get('remoteForm').value);

    let newUser : User = new User(null, this.generalFormGroup.get('passwordForm').value, this.generalFormGroup.get('emailForm').value,
      this.generalFormGroup.get('firstNameForm').value, this.generalFormGroup.get('lastNameForm').value,
      new Date(this.generalFormGroup.get('birthDateForm').value), this.generalFormGroup.get('genderForm').value, background,
     permission, location);

    this.accountService.register(newUser).subscribe( response => {
        this.registeredUser = response.body;
        this.snackBar.open("Registration Successful", "Go to login!", {duration: 3000})
        this.router.navigate(["login"]);
    },
      error => {
        console.log(error);
        this.snackBar.open("Email already taken", "Registration Failed!", {duration: 3000})
      });
  }

  onChosenLocation(event) {
    console.log(event);

    this.latitude = event.coords.lat;
    this.longitude= event.coords.lng;
    this.locationChosen = true;
  }
}
