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

  constructor(private jobService: JobService,
              private route: ActivatedRoute,
              private router: Router,
              private config: NgbRatingConfig) {
    config.max = 5;
  }

  ngOnInit(): void {
    this.route.params.pipe(switchMap((params: Params) => this.jobService.getJob(+params['jobID'])))
      .subscribe(response => {this.job = response.body;
        this.jobService.getJobPreferenceForUser(this.currentUser.id, this.job.id)
          .subscribe(response => {this.preference = response.body},
            error => console.log(error.error))});

  }

  vote(isInterested: boolean) {
    let preference: Preference = new Preference(0, this.currentUser, this.job, isInterested, false);
    this.jobService.savePreference(preference).subscribe();
    this.refresh();
  }

  refresh(): void{
    window.location.reload();
  }

  moreLikeThis(id: number) {
    this.jobService.getRecommendedJobsIds(id).subscribe(
      response => console.log(response)
    )
  }
}
