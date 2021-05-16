import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobService} from "../service/JobService";

@Component({
  selector: 'app-job-remove',
  templateUrl: './job-remove.component.html',
  styleUrls: ['./job-remove.component.css']
})
export class JobRemoveComponent implements OnInit {
  id: number;
  title: string;

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private jobService: JobService) {
    this.id = data.id;
    this.title = data.title;
  }

  ngOnInit(): void {
  }

  remove() {
    this.jobService.removeJob(this.id).subscribe();
  }
}
