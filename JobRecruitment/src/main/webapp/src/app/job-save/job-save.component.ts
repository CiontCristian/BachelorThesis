import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {JobService} from "../service/JobService";
import {FormControl, Validators} from "@angular/forms";
import {Job} from "../model/Job";
import {User} from "../model/User";
import {COMMA, ENTER, SPACE} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";
import {Contractor} from "../model/Contractor";
import {MatSnackBar} from "@angular/material/snack-bar";

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
  devTypes: string[] = [];
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
  availablePosForm = new FormControl('1', [Validators.required]);

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private jobService: JobService,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    console.log(this.contractor);
  }

  register(): void{
    let job: Job = new Job(0, this.titleForm.value, this.descriptionForm.value, this.jobTypeForm.value.toString(),
      this.remoteForm.value, this.minExpForm.value.toString(), this.minCompForm.value === '' ? -1
      : Math.abs(this.minCompForm.value), this.devTypes.toString(),
      this.techs.toString(), this.availablePosForm.value, new Date(Date.now()), this.contractor);

    console.log(job);
    this.jobService.saveJob(job).subscribe(
      response => {this.snackBar.open("Job added successfully!","",{duration: 3000})
      },
      error => console.log(error)
    );
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
  }
}
