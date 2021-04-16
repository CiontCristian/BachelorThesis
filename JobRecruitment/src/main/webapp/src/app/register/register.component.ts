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
      ]), passwordForm: new FormControl('', [Validators.required]),
      firstNameForm: new FormControl('', [Validators.required]), lastNameForm: new FormControl('', [Validators.required]),
      birthDateForm: new FormControl('', [Validators.required]), genderForm : new FormControl('', [Validators.required])
    });

    this.backgroundFormGroup = this.formBuilder.group({
      techsForm: new FormControl('', [Validators.required]), experienceForm: new FormControl('', [Validators.required]),
      permissionForm: new FormControl('', Validators.required), jobTypeForm: new FormControl('', Validators.required),
      devTypeForm: new FormControl('', Validators.required),remoteForm: new FormControl(false, Validators.required)
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
