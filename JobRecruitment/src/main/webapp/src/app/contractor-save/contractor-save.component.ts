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
  locationFormGroup: FormGroup;
  currentUser: User = null;
  contractor: Contractor = null;

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

    this.locationFormGroup = this.formBuilder.group({
      addressForm: new FormControl(''), cityForm: new FormControl(''),
      countryForm: new FormControl('')
    });
  }

  onFileChanged(event) {
    this.image = event.target.files[0]
  }

  register() {
    let location: Location = new Location(null, this.locationFormGroup.get('addressForm').value, this.locationFormGroup.get('cityForm').value,
      this.locationFormGroup.get('countryForm').value);

    this.formData.append("file", this.image, this.image.name);
    console.log(this.image);
    let contractor: Contractor = new Contractor(0, this.generalFormGroup.get('nameForm').value,
      this.generalFormGroup.get('descriptionForm').value, this.generalFormGroup.get('nrOfEmployeesForm').value,
      null, location, null);

    const contractorBlob = new Blob([JSON.stringify(contractor)],{ type: "application/json"})
    this.formData.append("contractorDTO", contractorBlob);
    this.contractorService.saveContractor(this.formData).subscribe(
      response => {this.contractor = response.body;
        this.accountService.modifyUserContractor(this.currentUser.id, this.contractor)
          .subscribe(
            response => {sessionStorage.setItem("currentUser", JSON.stringify(response.body));
              this.currentUser = response.body;
              console.log(this.currentUser)},
            error => console.log(error)
          );},
      error => console.log(error)
    );

  }
}
