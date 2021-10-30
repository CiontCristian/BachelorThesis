import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../model/Job";
import {User} from "../model/User";

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.css']
})
export class JobCardComponent implements OnInit {

  @Input()
  job: Job;
  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  constructor() { }

  ngOnInit(): void {
  }
}
