import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../model/Job";

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.css']
})
export class JobCardComponent implements OnInit {

  @Input()
  job: Job;

  constructor() { }

  ngOnInit(): void {
  }

}
