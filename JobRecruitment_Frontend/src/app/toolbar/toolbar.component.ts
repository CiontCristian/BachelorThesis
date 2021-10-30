import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {AccountService} from "../service/AccountService";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {JobService} from "../service/JobService";
import {Router} from "@angular/router";
import {Job} from "../model/Job";
import {JobListComponent} from "../job-list/job-list.component";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {JobModifyComponent} from "../job-modify/job-modify.component";
import {UserModifyComponent} from "../user-modify/user-modify.component";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  public currentUser : User;
  job: Job = null;
  searchForm: FormControl;
  jobTitles: string[] = [];
  filteredJobTitles: Observable<string[]>;

  constructor(private accountService: AccountService,
              private jobService: JobService,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"));

    this.jobService.findJobsTitles().subscribe(
      response => {this.jobTitles = response.body},
      error => console.log(error.error)
    )
    this.searchForm = new FormControl('');
    this.filteredJobTitles = this.searchForm.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );

  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.jobTitles.filter(option => option.toLowerCase().includes(filterValue));
  }

  logout() : void{
    this.accountService.logout();
    this.ngOnInit();
    this.router.navigate(['/login']);
  }

  goToJobDetails(title: string) {
    this.jobService.findJobByTitle(title).subscribe(
      response => {this.job = response.body;
        this.router.navigate(["job-list/details/", this.job.id]);
      },
      error => console.log(error.error)
    );
  }

  openModifyUserDialog(currentUser: User) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      currentUser
    }

    const dialogRef = this.dialog.open(UserModifyComponent,{
      width: '60%',
      data: dialogConfig.data
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result !== "cancel")
        window.location.reload();
    });
  }
}
