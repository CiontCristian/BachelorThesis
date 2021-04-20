import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {JobService} from "../service/JobService";
import {Job} from "../model/Job";
import {StatisticsService} from "../service/StatisticsService";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  currentUser: User;
  jobsIds: number[] = [];
  jobs: Job[] = [];
  single: any[] = [];

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };
  constructor( private jobService: JobService,
               private statisticsService: StatisticsService) {}

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"))
    this.statisticsService.getCompaniesWithNumberOfOffers()
      .subscribe(response => {
        this.single = response.body;
        console.log(response.body);
        console.log(this.single);
      })
  }

  getDatasetJobs() {
    this.jobService.getJobsFromDataset()
      .subscribe(response => {this.jobs = response.body;
          console.log(this.jobs);
          this.jobService.saveJobs(this.jobs).subscribe()
        },
        error => console.log(error.error))
  }

  test(){
    let job: Job = new Job(1,"Hello","","",true,"",800,
      "","",0,null);
    console.log(JSON.stringify(job))
  }
}
