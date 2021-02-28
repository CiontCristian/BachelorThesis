import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {AccountService} from "../service/AccountService";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {JobService} from "../service/JobService";
import {Router} from "@angular/router";
import {Job} from "../model/Job";

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
              private router: Router) { }

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
  }

  goToJobDetails(title: string) {
    this.jobService.findJobByTitle(title).subscribe(
      response => {this.job = response.body;
        this.router.navigate(["job-list/details/", this.job.id]);
      },
      error => console.log(error.error)
    );
    /*if(this.job)
      this.router.navigate(["job-list/details/", this.job.id]);*/
  }

  search(value: string) {
    this.jobService.sendSearchValue(value);
    this.router.navigate(["job-list"]);
  }
}
