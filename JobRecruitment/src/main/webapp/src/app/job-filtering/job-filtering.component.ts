import { Component, OnInit } from '@angular/core';
import {MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER, SPACE} from "@angular/cdk/keycodes";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-job-filtering',
  templateUrl: './job-filtering.component.html',
  styleUrls: ['./job-filtering.component.css']
})
export class JobFilteringComponent implements OnInit {

  jobTypeForm = new FormControl('');
  devTypeForm = new FormControl('');
  techsForm = new FormControl('');
  minExpForm = new FormControl('');

  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA, SPACE];
  techs: string[] = [];
  devTypes: string[] = [];

  constructor() { }

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

      //this.techsForm.setValue(null);
    }
    else{
      if ((value || '').trim()) {
        this.devTypes.push(value);
      }

      if (input) {
        input.value = '';
      }

      //this.devTypeForm.setValue(null);
    }
  }

}
