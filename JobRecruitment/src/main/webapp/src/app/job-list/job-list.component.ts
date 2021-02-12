import {Component, Input, OnInit} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Job} from "../model/Job";
import {JobService} from "../service/JobService";
import {Router} from "@angular/router";
import {ContractorService} from "../service/ContractorService";
import {Contractor} from "../model/Contractor";
import {FileProperties} from "../model/FileProperties";


@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.css']
})
export class JobListComponent implements OnInit {
  //paginator: MatPaginator;
  jobs: Job[] = null;
  pageEvent: PageEvent;
  pageIndex: number=0;
  pageSize: number=5;
  recordCount: number;
  pageSizeOptions: number[] = [5,10,50,100];

  constructor(private jobService: JobService,
              private contractorService: ContractorService,
              private router: Router) { }

  ngOnInit(): void {
    this.getAllJobs();
  }

  getAllJobs(): void{
    this.jobService.getAllJobs(this.pageIndex, this.pageSize)
      .subscribe(response => this.jobs = response.body);
  }

  pageNavigations(event: PageEvent) {
    console.log(event);
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.objects = this.testData.slice(this.Page, this.Page + 2)
    this.jobService.getAllJobs(this.pageIndex, this.pageSize)
      .subscribe(response => this.jobs = response.body);

    //this.recordCount = this.testData.length
    this.recordCount = this.jobs.length;

    return event
  }

  navigateToJobDetails(id: number){
    this.router.navigate(["job-list/details/", id]);
  }
}
