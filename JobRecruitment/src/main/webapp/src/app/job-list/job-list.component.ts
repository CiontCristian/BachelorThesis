import {Component, Input, OnInit} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Job} from "../model/Job";
import {JobService} from "../service/JobService";
import {Router} from "@angular/router";
import {ContractorService} from "../service/ContractorService";
import {Contractor} from "../model/Contractor";
import {FileProperties} from "../model/FileProperties";
import {User} from "../model/User";
import {Filter} from "../model/Filter";
import {$e} from "codelyzer/angular/styles/chars";


@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.css']
})
export class JobListComponent implements OnInit {

  /*@Input()
  fromHome: boolean = false;*/
  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  currentFilter: Filter = new Filter("","",null,"",null,"","",null);
  jobs: Job[] = null;
  recommendedJobs: Job[] = null;
  pageEvent: PageEvent;
  pageIndex: number=0;
  pageSize: number=12;
  recordCount: number = null;
  pageSizeOptions: number[] = [12,24,100];
  searchValue: string = "";

  constructor(private jobService: JobService,
              private contractorService: ContractorService,
              private router: Router) { }

  ngOnInit(): void {
    this.jobService.getJobRecordCount().subscribe(
      response => this.recordCount = response.body
    )
    this.getAllJobs();
    if(this.currentUser !== null){
      this.jobService.getRecommendedJobsIdsCBF(this.currentUser.id).subscribe(
        response => this.jobService.getJobsByIds(8, response.body).subscribe(
          response => this.recommendedJobs = response.body
        )
      );
    }
  }

  getAllJobs(): void{
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.currentFilter)
      .subscribe(response => this.jobs = response.body);
  }

  pageNavigations(event: PageEvent) {
    console.log(event);
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.objects = this.testData.slice(this.Page, this.Page + 2)
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.currentFilter)
      .subscribe(response => this.jobs = response.body);

    return event
  }

  navigateToJobDetails(id: number){
    this.router.navigate(["job-list/details/", id]);
  }

  viewFilter($event: Filter) {
    console.log($event);
    this.currentFilter = $event;
  }
}
