import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../model/Job";
import {JobService} from "../service/JobService";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-job-details',
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.css']
})
export class JobDetailsComponent implements OnInit {

  @Input()
  job: Job = null;

  constructor(private jobService: JobService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.params.pipe(switchMap((params: Params) => this.jobService.getJob(+params['jobID'])))
      .subscribe(response => this.job = response.body);
  }

}
