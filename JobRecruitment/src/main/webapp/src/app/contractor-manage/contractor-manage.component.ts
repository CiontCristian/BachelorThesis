import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
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
import {StatisticsService} from "../service/StatisticsService";

@Component({
  selector: 'app-contractor-manage',
  templateUrl: './contractor-manage.component.html',
  styleUrls: ['./contractor-manage.component.css']
})
export class ContractorManageComponent implements OnInit {

  jobs: Job[] = [];
  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  contractor: Contractor = null;

  generalFormGroup: FormGroup;
  latitude: number;
  longitude: number;
  latCluj = 46.74898191760513;
  lngCluj = 23.629722860492784;
  locationChosen: boolean = false;

  @ViewChild('imageUpload', {static: false}) imageUpload: ElementRef;
  image: File = null;
  imageName: string = '';
  formData = new FormData();

  dataSource: MatTableDataSource<User>;
  columnsToDisplay = ['name', 'dob', 'email', 'experience'];

  horizontalChart: any[] = [];

  constructor(private contractorService: ContractorService,
              private jobService: JobService,
              private accountService: AccountService,
              private statisticsService: StatisticsService,
              private router: Router,
              private snackBar: MatSnackBar,
              private dialog: MatDialog,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    if(this.contractor === null)
      this.contractor = JSON.parse(sessionStorage.getItem("contractor"));
    console.log(this.currentUser);
    console.log(this.contractor);

    if(this.contractor !== null) {
      this.imageName = this.contractor.logo.name;
      this.dataSource = new MatTableDataSource<User>();
      this.latitude = this.contractor.location.latitude;
      this.longitude = this.contractor.location.longitude;
      this.jobService.findJobsForContractor(this.contractor.id).subscribe(
        response => {this.jobs = response.body},
        error => {console.log(error)}
      )

      this.generalFormGroup = this.formBuilder.group({
        nameForm: new FormControl(this.contractor.name), descriptionForm: new FormControl(this.contractor.description),
        nrOfEmployeesForm: new FormControl(this.contractor.nrOfEmployees), logoForm: new FormControl('')
      })

      this.statisticsService.mostAppliedJobsForContractor(this.contractor.id)
        .subscribe(response => {
          this.horizontalChart = response.body;
          console.log(response.body);
          console.log(this.horizontalChart);
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
      if(result !== "cancel") {
        setTimeout(() => {
          this.contractorService.findContractorForUser(this.currentUser.id)
            .subscribe(response => {
              this.contractor = response.body;
              console.log("After dialog close");
              console.log(response.body);
              sessionStorage.setItem("contractor", JSON.stringify(response.body));
              window.location.reload();
            })
        }, 1000);
      }
      //window.location.reload();
    });
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



  modify() {
    let location: Location = new Location(this.contractor.location.id, this.latitude, this.longitude);
    let contractor: Contractor;
    if(this.image !== null) {
      this.formData.append("file", this.image, this.image.name);
      contractor = new Contractor(this.contractor.id, this.generalFormGroup.get('nameForm').value,
        this.generalFormGroup.get('descriptionForm').value, this.generalFormGroup.get('nrOfEmployeesForm').value,
        null, location, this.currentUser);
    }
    else{
      this.formData.append("file", new Blob([]),"no_change");
      contractor = new Contractor(this.contractor.id, this.generalFormGroup.get('nameForm').value,
        this.generalFormGroup.get('descriptionForm').value, this.generalFormGroup.get('nrOfEmployeesForm').value,
        this.contractor.logo, location, this.currentUser);
    }

    const contractorBlob = new Blob([JSON.stringify(contractor)],{ type: "application/json"})
    this.formData.append("contractorDTO", contractorBlob);
    this.formData.append("logoID", this.contractor.logo.id.toString())
    this.contractorService.modifyContractor(this.formData).subscribe(
      response => {this.contractor = response.body;
        sessionStorage.setItem("contractor", JSON.stringify(response.body));
        this.refresh();
        },
      error => console.log(error)
    );
  }

  refresh(){
    window.location.reload();
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

  onChosenLocation(event) {
    console.log(event);

    this.latitude = event.coords.lat;
    this.longitude= event.coords.lng;
    this.locationChosen = true;
  }
}
