import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AccountService} from "../service/AccountService";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ContractorService} from "../service/ContractorService";
import {Contractor} from "../model/Contractor";
import {Location} from "../model/Location";
import {User} from "../model/User";

@Component({
  selector: 'app-contractor-save',
  templateUrl: './contractor-save.component.html',
  styleUrls: ['./contractor-save.component.css']
})
export class ContractorSaveComponent implements OnInit {

  generalFormGroup: FormGroup;
  latitude: number;
  longitude: number;
  currentUser: User = null;
  latCluj = 46.74898191760513;
  lngCluj = 23.629722860492784;
  locationChosen: boolean = false;

  image: File;
  formData = new FormData();

  constructor(private contractorService: ContractorService,
              private accountService: AccountService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"));

    this.generalFormGroup = this.formBuilder.group({
      nameForm: new FormControl(''), descriptionForm: new FormControl(''),
      nrOfEmployeesForm: new FormControl(''), logoForm: new FormControl('')
    })

  }

  onFileChanged(event) {
    this.image = event.target.files[0]

  }

  register() {
    let location: Location = new Location(null, this.latitude, this.longitude);

    this.formData.append("file", this.image, this.image.name);
    console.log(this.image);
    let contractor: Contractor = new Contractor(0, this.generalFormGroup.get('nameForm').value,
      this.generalFormGroup.get('descriptionForm').value, this.generalFormGroup.get('nrOfEmployeesForm').value,
      null, location,  this.currentUser);

    const contractorBlob = new Blob([JSON.stringify(contractor)],{ type: "application/json"})
    this.formData.append("contractor", contractorBlob);
    this.contractorService.saveContractor(this.formData).subscribe(
      response => {
        //sessionStorage.setItem("contractor", JSON.stringify(response.body))
        //console.log("In add contractor dialog " + response.body);
      },
      error => console.log(error)
    );
  }

  onChosenLocation(event) {
    console.log(event);

    this.latitude = event.coords.lat;
    this.longitude= event.coords.lng;
    this.locationChosen = true;
  }
}
