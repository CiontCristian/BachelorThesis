import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobService} from "../service/JobService";
import {User} from "../model/User";
import {Contractor} from "../model/Contractor";
import {FormControl, Validators} from "@angular/forms";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {Job} from "../model/Job";
import {MatChipInputEvent} from "@angular/material/chips";

@Component({
  selector: 'app-job-modify',
  templateUrl: './job-modify.component.html',
  styleUrls: ['./job-modify.component.css']
})
export class JobModifyComponent implements OnInit {

  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  job: Job = null;

  titleForm = new FormControl('', [Validators.required]);
  descriptionForm = new FormControl('', [Validators.required]);
  jobTypeForm = new FormControl('', [Validators.required]);
  jobTypes: string[] = ["part-time", "full-time", "internship"];
  devTypeForm = new FormControl('', [Validators.required]);
  devTypes: string[] = [];
  remoteForm = new FormControl('');
  remote: boolean;
  minExpForm = new FormControl('', [Validators.required]);
  experienceLevels: string[] = ["entry", "junior", "middle", "senior", "lead", "manager"];
  techsForm = new FormControl('', [Validators.required]);
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  techs: string[] = [];

  minCompForm = new FormControl('');
  availablePosForm = new FormControl('', [Validators.required]);

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private jobService: JobService) {
    this.job = data.job;
    this.titleForm.setValue(this.job.title);
    this.descriptionForm.setValue(this.job.description);
    this.jobTypeForm.setValue(this.job.jobType.split(','));
    this.devTypeForm.setValue(this.job.devType.split(','));
    this.remoteForm.setValue(this.setRemote(this.job.remote));
    this.minExpForm.setValue(this.job.minExperience.split(','));
    //this.techsForm.setValue(this.job.techs.split(','));
    this.techs = this.job.techs.split(',');
    this.minCompForm.setValue(this.job.minCompensation);
    this.availablePosForm.setValue(this.job.availablePos);

  }

  ngOnInit(): void {
  }

  remove(item: string, type: string) {
    if(type === "tech"){
      const index = this.techs.indexOf(item);

      if (index >= 0) {
        this.techs.splice(index, 1);
      }
    }
    else{
      const index = this.devTypes.indexOf(item);

      if (index >= 0) {
        this.devTypes.splice(index, 1);
      }
    }
  }

  add(event: MatChipInputEvent, type: string) {
    const input = event.input;
    const value = event.value;

    if(type === "tech"){
      if ((value || '').trim()) {
        this.techs.push(value);
      }

      if (input) {
        input.value = '';
      }

      this.techsForm.setValue(null);
    }
    else{
      if ((value || '').trim()) {
        this.devTypes.push(value);
      }

      if (input) {
        input.value = '';
      }

      this.devTypeForm.setValue(null);
    }
  }

  setRemote(checked: boolean) {
    this.remote = checked;
    console.log(this.remote);
    console.log(this.remoteForm.value);
  }

  modify() {
    let job: Job = new Job(this.job.id, this.titleForm.value, this.descriptionForm.value, this.jobTypeForm.value.toString(),
      this.remoteForm.value, this.minExpForm.value.toString(), this.minCompForm.value === '' ? -1
        : this.minCompForm.value, this.devTypes.toString(),
      this.techs.toString(), this.availablePosForm.value, this.job.dateAdded, this.job.contractor);

    this.jobService.modifyJob(job).subscribe();
  }
}
