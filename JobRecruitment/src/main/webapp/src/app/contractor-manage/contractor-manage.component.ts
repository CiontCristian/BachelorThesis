import { Component, OnInit } from '@angular/core';
import {ContractorService} from "../service/ContractorService";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {JobSaveComponent} from "../job-save/job-save.component";
import {Job} from "../model/Job";
import {User} from "../model/User";
import {JobService} from "../service/JobService";
import {Contractor} from "../model/Contractor";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {Location} from "../model/Location";
import {JobModifyComponent} from "../job-modify/job-modify.component";
import {JobRemoveComponent} from "../job-remove/job-remove.component";
import {ContractorSaveComponent} from "../contractor-save/contractor-save.component";
import {MatTableDataSource} from "@angular/material/table";
import {AccountService} from "../service/AccountService";

@Component({
  selector: 'app-contractor-manage',
  templateUrl: './contractor-manage.component.html',
  styleUrls: ['./contractor-manage.component.css']
})
export class ContractorManageComponent implements OnInit {

  jobs: Job[] = [];
  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  contractor: Contractor = JSON.parse(sessionStorage.getItem("contractor"));

  generalFormGroup: FormGroup;
  locationFormGroup: FormGroup;

  image: File = null;
  formData = new FormData();

  dataSource: MatTableDataSource<User>;
  columnsToDisplay = ['name', 'dob', 'email', 'education'];

  constructor(private contractorService: ContractorService,
              private jobService: JobService,
              private accountService: AccountService,
              private router: Router,
              private snackBar: MatSnackBar,
              private dialog: MatDialog,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    console.log(this.currentUser);
    console.log(this.contractor);

    if(this.contractor) {
      this.dataSource = new MatTableDataSource<User>();
      this.jobService.findJobsForContractor(this.contractor.id).subscribe(
        response => {this.jobs = response.body},
        error => {console.log(error)}
      )

      this.generalFormGroup = this.formBuilder.group({
        nameForm: new FormControl(this.contractor.name), descriptionForm: new FormControl(this.contractor.description),
        nrOfEmployeesForm: new FormControl(this.contractor.nrOfEmployees), logoForm: new FormControl('')
      })

      this.locationFormGroup = this.formBuilder.group({
        addressForm: new FormControl(this.contractor.location.address), cityForm: new FormControl(this.contractor.location.city),
        countryForm: new FormControl(this.contractor.location.country)
      });
    }
  }

  openAddJobDialog(): void{
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {

    }

    const dialogRef = this.dialog.open(JobSaveComponent,{
      width: '50%',
      height: '80vh',
      data: dialogConfig.data
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if(result !== "cancel")
        window.location.reload();
    });
  }

  openAddContractorDialog(): void{
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {

    }

    const dialogRef = this.dialog.open(ContractorSaveComponent,{
      width: '50%',
      height: '80vh',
      data: dialogConfig.data
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if(result !== "cancel")
        this.contractorService.findContractorForUser(this.currentUser.id)
          .subscribe(response => {this.contractor = response.body;
            console.log(this.contractor);
            sessionStorage.setItem("contractor", JSON.stringify(this.contractor));
            window.location.reload();
          })
      //window.location.reload();
    });
  }

  onFileChanged(event) {
    this.image = event.target.files[0]
  }

  modify() {
    let location: Location = new Location(this.contractor.location.id, this.locationFormGroup.get('addressForm').value, this.locationFormGroup.get('cityForm').value,
      this.locationFormGroup.get('countryForm').value);

    this.formData.append("file", this.image, this.image.name);
    console.log(this.image);
    let contractor: Contractor = new Contractor(this.contractor.id, this.generalFormGroup.get('nameForm').value,
      this.generalFormGroup.get('descriptionForm').value, this.generalFormGroup.get('nrOfEmployeesForm').value,
      null, location, this.currentUser);

    const contractorBlob = new Blob([JSON.stringify(contractor)],{ type: "application/json"})
    this.formData.append("contractorDTO", contractorBlob);
    this.formData.append("logoID", this.contractor.logo.id.toString())
    this.contractorService.modifyContractor(this.formData).subscribe(
      response => {this.contractor = response.body;
        sessionStorage.setItem("contractor", JSON.stringify(response.body));
        },
      error => console.log(error)
    );
  }

  openModifyJobDialog(job: Job) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      job
    }

    const dialogRef = this.dialog.open(JobModifyComponent,{
      width: '50%',
      height: '80vh',
      data: dialogConfig.data
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result !== "cancel")
        window.location.reload();
    });
  }

  openRemoveJobDialog(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      id
    }

    const dialogRef = this.dialog.open(JobRemoveComponent,{
      width: '300px',
      height: '300px',
      data: dialogConfig.data
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result !== "cancel")
        window.location.reload();
    });
  }

  getJobsCandidates(id: number) {
    this.accountService.getJobCandidates(id)
      .subscribe(
        response => {this.dataSource.data = response.body},
        error => console.log(error.error)
      )
  }
}
