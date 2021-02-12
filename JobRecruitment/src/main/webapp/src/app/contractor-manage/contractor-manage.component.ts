import { Component, OnInit } from '@angular/core';
import {ContractorService} from "../service/ContractorService";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {JobSaveComponent} from "../job-save/job-save.component";
import {Job} from "../model/Job";
import {User} from "../model/User";
import {JobService} from "../service/JobService";

@Component({
  selector: 'app-contractor-manage',
  templateUrl: './contractor-manage.component.html',
  styleUrls: ['./contractor-manage.component.css']
})
export class ContractorManageComponent implements OnInit {

  jobs: Job[] = [];
  currentUser: User = null;

  constructor(private contractorService: ContractorService,
              private jobService: JobService,
              private router: Router,
              private snackBar: MatSnackBar,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
    console.log(this.currentUser);
    this.jobs = this.currentUser.company.offers;
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
      window.location.reload();
    });
  }


}
