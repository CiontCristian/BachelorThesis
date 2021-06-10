import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../model/Job";
import {JobService} from "../service/JobService";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Preference} from "../model/Preference";
import {User} from "../model/User";
import {Contractor} from "../model/Contractor";
import {NgbRatingConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-job-details',
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.css']
})
export class JobDetailsComponent implements OnInit {

  @Input()
  job: Job = null;
  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  preference: Preference = null;
  similarJobs: Job[] = null;
  latitudeUser: number;
  longitudeUser: number;
  toggleAccuracy: boolean = false;
  accuracy: number = 0;

  constructor(private jobService: JobService,
              private route: ActivatedRoute,
              private router: Router,
              private config: NgbRatingConfig) {
    config.max = 5;
  }

  ngOnInit(): void {
    this.route.params.pipe(switchMap((params: Params) => this.jobService.getJob(+params['jobID'])))
      .subscribe(response => {this.job = response.body;
        if(this.currentUser !== null && this.currentUser.permission.isClient)
          this.jobService.getJobPreferenceForUser(this.currentUser.id, this.job.id)
            .subscribe(response => {this.preference = response.body},
              error => console.log(error.error))});

    if(this.currentUser && this.currentUser.location){
      this.latitudeUser = this.currentUser.location.latitude;
      this.longitudeUser = this.currentUser.location.longitude;
    }
    this.similarJobs = null;

  }

  rateJob(isInterested: boolean) {
    if(this.preference === null) {
      let preference: Preference = new Preference(0, this.currentUser, this.job, isInterested, false, null, null);
      this.jobService.savePreference(preference).subscribe();
      this.refresh();
    }
    else{
      this.preference.interested = isInterested;
      this.jobService.savePreference(this.preference).subscribe();
      this.refresh();
    }
  }

  refresh(): void{
    window.location.reload();
  }

  moreLikeThis(jobId: number) {
    let currentUserId: number;
    if(this.currentUser === null || !this.currentUser.permission.isClient){
      currentUserId = -1;
    }
    else{
      currentUserId = this.currentUser.id;
    }
    this.jobService.getRecommendedJobsIdsKNN(jobId, currentUserId).subscribe(
      response => {
        this.jobService.getJobsByIds(7, response.body).subscribe(
          response => { this.similarJobs = response.body},
          error => console.log(error.error)
        )
      },
      error => console.log(error.error)
    )
  }

  navigateToJobDetails(id: number) {
    this.router.navigate(["job-list/details/", id]);
    //this.refresh();
  }

  apply() {
    if(this.preference === null){
      let preference: Preference = new Preference(0, this.currentUser, this.job, null, true, null, null);
      this.jobService.savePreference(preference).subscribe();
      this.refresh();
    }
    else{
      this.preference.applied = true;
      this.jobService.savePreference(this.preference).subscribe();
      this.refresh();
    }
  }

  rad(x: number){
    return x * Math.PI / 180;
  };

  getHaversineDistance(lat1, lat2, lng1, lng2) {

    var R = 6378137; // Earth’s mean radius in meters
    var dLat = this.rad(lat2 - lat1);
    var dLong = this.rad(lng2 - lng1);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.rad(lat1)) * Math.cos(this.rad(lat2)) *
      Math.sin(dLong / 2) * Math.sin(dLong / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var km = (R * c) / 1000; //distance in km
    return Math.round((km + Number.EPSILON) * 100) / 100
  };

  removeJob(id: number) {
    this.jobService.removeJob(id)
      .subscribe(response => console.log("Job Removed!"));
  }

  rateRecommendation(relevanceSec: boolean, job: Job) {
    let preference: Preference;
    this.jobService.getJobPreferenceForUser(this.currentUser.id, job.id)
      .subscribe(response => {preference = response.body;
          preference.relevanceSec = relevanceSec;
          this.jobService.savePreference(preference).subscribe()},
        error => {console.log(error.error);
          preference = new Preference(0, this.currentUser, job, null, false, null, relevanceSec);
          this.jobService.savePreference(preference).subscribe();});
  }

  showAccuracy() {
    if(this.toggleAccuracy){
      this.toggleAccuracy = !this.toggleAccuracy;
      return;
    }
    this.jobService.computeSecondaryRecommenderAccuracy(this.currentUser.id).subscribe(
      response => {this.accuracy = response.body;
        this.toggleAccuracy = !this.toggleAccuracy;}
    );
  }
}
