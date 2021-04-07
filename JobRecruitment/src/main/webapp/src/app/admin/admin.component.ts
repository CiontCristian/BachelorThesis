import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {JobService} from "../service/JobService";
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
  constructor( private jobService: JobService) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"))
  }

  getDatasetJobs() {
    this.jobService.getJobsFromDataset()
      .subscribe(response => {this.jobs = response.body;
          console.log(this.jobs);
          this.jobService.saveJobs(this.jobs).subscribe()
        },
        error => console.log(error.error))
  }
}
