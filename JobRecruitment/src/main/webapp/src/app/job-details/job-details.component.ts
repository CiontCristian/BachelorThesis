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

  constructor(private jobService: JobService,
              private route: ActivatedRoute,
              private router: Router,
              private config: NgbRatingConfig) {
    config.max = 5;
  }

  ngOnInit(): void {
    this.route.params.pipe(switchMap((params: Params) => this.jobService.getJob(+params['jobID'])))
      .subscribe(response => {this.job = response.body;
        if(this.currentUser !== null)
          this.jobService.getJobPreferenceForUser(this.currentUser.id, this.job.id)
            .subscribe(response => {this.preference = response.body},
              error => console.log(error.error))});
    this.similarJobs = null;

  }

  vote(isInterested: boolean) {
    if(this.preference === null) {
      let preference: Preference = new Preference(0, this.currentUser, this.job, isInterested, false);
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

  moreLikeThis(id: number) {
    this.jobService.getRecommendedJobsIdsKNN(id).subscribe(
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
    this.refresh();
  }

  apply() {
    if(this.preference === null){
      let preference: Preference = new Preference(0, this.currentUser, this.job, null, true);
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

    var R = 6378137; // Earth’s mean radius in meter
    var dLat = this.rad(lat2 - lat1);
    var dLong = this.rad(lng2 - lng1);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.rad(lat1)) * Math.cos(this.rad(lat2)) *
      Math.sin(dLong / 2) * Math.sin(dLong / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // returns the distance in meter
  };
}
