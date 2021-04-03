import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {JobService} from "../service/JobService";
import {RecommenderInfo} from "../model/RecommenderInfo";
import {Job} from "../model/Job";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  currentUser: User;
  jobsIds: number[] = [];
  jobs: Job[] = [];
  info: RecommenderInfo;
  constructor( private jobService: JobService) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"))
  }

  getRecommenderIds() {
    this.jobService.getRecommendedJobsIds()
      .subscribe(response => {this.jobsIds = response.body;
      console.log(this.jobsIds)},
        error => console.log(error.error))
  }

  saveRecommenderInfo(){
    let info: RecommenderInfo = new RecommenderInfo(this.currentUser.id, "knn");

    this.jobService.saveRecommenderInfo(info)
      .subscribe(response => {this.info = response.body},
        error => console.log(error.error))
  }

  getDatasetJobs() {
    this.jobService.getJobsFromDataset()
      .subscribe(response => {this.jobs = response.body;
          console.log(this.jobs)},
        error => console.log(error.error))
  }
}
