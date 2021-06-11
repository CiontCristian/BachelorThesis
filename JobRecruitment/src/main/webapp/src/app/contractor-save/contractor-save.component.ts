import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
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

  @ViewChild('imageUpload', {static: false}) imageUpload: ElementRef;
  image: File = null;
  imageName: string = '';
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

  uploadImageEvent() {
    const imageUpload = this.imageUpload.nativeElement;
    imageUpload.onchange = () => {
      this.image = imageUpload.files[0];
      this.imageName = this.image.name;
      this.imageUpload.nativeElement.value = '';
    };
    imageUpload.click();
  }

  register() {
    let location: Location = new Location(null, this.latitude, this.longitude);

    console.log(this.image.type);
    if(this.image.type !== "image/png" && this.image.type !== "image/jpeg"){
      this.snackBar.open("Please upload an image in .png or .jpg format!", "Retry!", {duration: 3000});
      return;
    }

    this.formData.append("file", this.image, this.image.name);
    let contractor: Contractor = new Contractor(0, this.generalFormGroup.get('nameForm').value,
      this.generalFormGroup.get('descriptionForm').value, Math.abs(this.generalFormGroup.get('nrOfEmployeesForm').value),
      null, location,  this.currentUser);

    const contractorBlob = new Blob([JSON.stringify(contractor)],{ type: "application/json"})
    this.formData.append("contractor", contractorBlob);
    this.contractorService.saveContractor(this.formData).subscribe(
      response => {
        this.snackBar.open("Company registered successfully", "You can start adding offers",
          {duration: 3000})
      },
      error => {console.log(error);
        this.snackBar.open("Company registration failed", "Try Again!",
          {duration: 3000}) }
    );
  }

  onChosenLocation(event) {
    console.log(event);

    this.latitude = event.coords.lat;
    this.longitude= event.coords.lng;
    this.locationChosen = true;
  }
}
