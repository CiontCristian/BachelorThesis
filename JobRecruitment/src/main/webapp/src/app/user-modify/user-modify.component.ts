import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {User} from "../model/User";
import {AccountService} from "../service/AccountService";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {JobService} from "../service/JobService";
import {Location} from "../model/Location";
import {Background} from "../model/Background";

@Component({
  selector: 'app-user-modify',
  templateUrl: './user-modify.component.html',
  styleUrls: ['./user-modify.component.css']
})
export class UserModifyComponent implements OnInit {
  currentUser: User;
  generalFormGroup: FormGroup;
  backgroundFormGroup: FormGroup;
  latitude: number;
  longitude: number;
  latCluj = 46.74898191760513;
  lngCluj = 23.629722860492784;
  availableTechs: string[] = [];
  availableDevTypes: string[] = [];
  hidePassword: boolean = true;
  jobTypes: string[] = ["part-time", "full-time", "internship"];

  experienceLevels: string[] = ["entry", "junior", "middle", "senior", "lead", "manager"];

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private accountService: AccountService,
              private jobService: JobService,
              private formBuilder: FormBuilder) {
    this.currentUser = data.currentUser;
  }

  ngOnInit(): void {
    this.generalFormGroup = this.formBuilder.group({
      emailForm : new FormControl(this.currentUser.email, [
        Validators.required,
        Validators.email
      ]), passwordForm: new FormControl(this.currentUser.password, [Validators.required, Validators.minLength(4)]),
      firstNameForm: new FormControl(this.currentUser.firstName, [Validators.required]), lastNameForm: new FormControl(this.currentUser.lastName, [Validators.required]),
      birthDateForm: new FormControl(this.currentUser.dateOfBirth, [Validators.required]), genderForm : new FormControl(this.currentUser.gender, [Validators.required])
    });

    if(this.currentUser.permission.isClient){
      this.backgroundFormGroup = this.formBuilder.group({
        techsForm: new FormControl(this.currentUser.background.techs.split(',')),
        experienceForm: new FormControl(this.currentUser.background.experience),
        jobTypeForm: new FormControl(this.currentUser.background.jobType.split(',')),
        devTypeForm: new FormControl(this.currentUser.background.devType.split(',')),
        remoteForm: new FormControl(this.currentUser.background.remote)
      });

    this.jobService.getAvailableTechs()
      .subscribe(response => {this.availableTechs = response.body},
        error => console.log(error.error))
    this.jobService.getAvailableDevTypes()
      .subscribe(response => {this.availableDevTypes = response.body},
        error => console.log(error.error))
    }
    if(!this.currentUser.permission.isAdmin){
      this.latitude = this.currentUser.location.latitude;
      this.longitude = this.currentUser.location.longitude;
    }
  }

  onChosenLocation(event) {
    console.log(event);

    this.latitude = event.coords.lat;
    this.longitude= event.coords.lng;
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


  modify() {
    let location: Location = new Location(this.currentUser.location.id, this.latitude, this.longitude);
    let background: Background;

    if(this.currentUser.permission.isClient){
      background = new Background(this.currentUser.background.id, this.backgroundFormGroup.get('techsForm').value.toString(),
        this.backgroundFormGroup.get('experienceForm').value, this.backgroundFormGroup.get('jobTypeForm').value.toString(),
        this.backgroundFormGroup.get('devTypeForm').value.toString(), this.backgroundFormGroup.get('remoteForm').value);
    }
    else{
      background = this.currentUser.background;
    }


    let modifiedUser : User = new User(this.currentUser.id, this.generalFormGroup.get('passwordForm').value, this.generalFormGroup.get('emailForm').value,
      this.generalFormGroup.get('firstNameForm').value, this.generalFormGroup.get('lastNameForm').value,
      new Date(this.generalFormGroup.get('birthDateForm').value), this.generalFormGroup.get('genderForm').value, background,
      this.currentUser.permission, location);

    this.accountService.modify(modifiedUser).subscribe(
      response => {sessionStorage.setItem("currentUser", JSON.stringify(response.body))}
    );
  }
}
