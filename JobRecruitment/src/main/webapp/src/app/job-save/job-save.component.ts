import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobService} from "../service/JobService";
import {FormControl, Validators} from "@angular/forms";
import {Job} from "../model/Job";
import {User} from "../model/User";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";
import {Contractor} from "../model/Contractor";

@Component({
  selector: 'app-job-save',
  templateUrl: './job-save.component.html',
  styleUrls: ['./job-save.component.css']
})
export class JobSaveComponent implements OnInit {

  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  contractor: Contractor = JSON.parse(sessionStorage.getItem("contractor"));

  titleForm = new FormControl('', [Validators.required]);
  descriptionForm = new FormControl('', [Validators.required]);
  jobTypeForm = new FormControl('', [Validators.required]);
  jobTypes: string[] = ["part-time", "full-time", "internship"];
  devTypeForm = new FormControl('', [Validators.required]);
  devTypes: string[] = ["Full Stack Developer", "Backend Developer", "Frontend Developer", "QA/Tester", "Designer",
    "Mobile Developer"];
  remoteForm = new FormControl('');
  remote: boolean = false;
  minExpForm = new FormControl('', [Validators.required]);
  experienceLevels: string[] = ["entry", "junior", "middle", "senior", "lead", "manager"];
  techsForm = new FormControl('', [Validators.required]);
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  techs: string[] = [];

  minCompForm = new FormControl('');
  availablePosForm = new FormControl('', [Validators.required]);

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private jobService: JobService) { }

  ngOnInit(): void {
  }

  register(): void{
    let job: Job = new Job(0, this.titleForm.value, this.descriptionForm.value, this.jobTypeForm.value.toString(),
      this.remoteForm.value, this.minExpForm.value.toString(), this.minCompForm.value === '' ? -1
      : this.minCompForm.value, this.devTypeForm.value.toString(),
      this.techs.toString(), this.availablePosForm.value, this.contractor);

    console.log(job);
    this.jobService.saveJob(job).subscribe(
      response => {
      },
      error => console.log(error)
    );
  }

  remove(tech: string) {
    const index = this.techs.indexOf(tech);

    if (index >= 0) {
      this.techs.splice(index, 1);
    }
  }

  add(event: MatChipInputEvent) {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.techs.push(value);
    }

    if (input) {
      input.value = '';
    }

    this.techsForm.setValue(null);
  }

  setRemote(checked: boolean) {
    this.remote = checked;
    console.log(this.remote);
    console.log(this.remoteForm.value);
  }
}
