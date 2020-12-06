import {Component, Input, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.css']
})
export class JobListComponent implements OnInit {
  testData: string[] = ["ala", "bala", "portocala", "tom", "si", "jerry", "cheama", "pompierii"]
  @Input('data') objects: string[] = this.testData.slice(0, 2)
  pageEvent: PageEvent;
  Page: number=0;
  Size: number=5;
  recordCount: number;
  pageSizeOptions: number[] = [5,10,50,100];
  constructor() { }

  ngOnInit(): void {
  }

  pageNavigations(event: PageEvent) {
    console.log(event);
    this.Page = event.pageIndex;
    this.Size = event.pageSize;
    this.objects = this.testData.slice(this.Page, this.Page + 2)

    this.recordCount = this.testData.length
    return event
  }

}
