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
  @Input()
  fromHome: boolean = false;
  jobs: Job[] = null;
  pageEvent: PageEvent;
  pageIndex: number=0;
  pageSize: number=9;
  recordCount: number;
  pageSizeOptions: number[] = [9,27,100];
  searchValue: string = "";

  constructor(private jobService: JobService,
              private contractorService: ContractorService,
              private router: Router) { }

  ngOnInit(): void {
    this.jobService.receiveSearchValue().subscribe(
      response => {this.searchValue = response;
                //this.getAllJobs();
                //this.jobService.sendSearchValue("");
      }
    );
    this.getAllJobs();

  }

  getAllJobs(): void{
    console.log(this.searchValue);
    if(this.fromHome)
      this.searchValue = '';
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.searchValue)
      .subscribe(response => this.jobs = response.body);
  }

  pageNavigations(event: PageEvent) {
    console.log(event);
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.objects = this.testData.slice(this.Page, this.Page + 2)
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.searchValue)
      .subscribe(response => this.jobs = response.body);

    //this.recordCount = this.testData.length
    //todo fix job record count
    this.recordCount = 50;

    return event
  }

  navigateToJobDetails(id: number){
    this.router.navigate(["job-list/details/", id]);
  }
}
