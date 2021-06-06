import {Component, Input, OnInit} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Job} from "../model/Job";
import {JobService} from "../service/JobService";
import {Router} from "@angular/router";
import {ContractorService} from "../service/ContractorService";
import {Contractor} from "../model/Contractor";
import {FileProperties} from "../model/FileProperties";
import {User} from "../model/User";
import {Filter} from "../model/Filter";
import {$e} from "codelyzer/angular/styles/chars";
import {StatisticsService} from "../service/StatisticsService";
import {Preference} from "../model/Preference";


@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.css']
})
export class JobListComponent implements OnInit {

  currentUser: User = JSON.parse(sessionStorage.getItem("currentUser"));
  currentFilter: Filter = new Filter(null,null,null,null,null,
    null,null,null);
  jobs: Job[] = null;
  recommendedJobs: Job[] = null;
  pageEvent: PageEvent;
  pageIndex: number=0;
  pageSize: number=12;
  recordCount: number = null;
  pageSizeOptions: number[] = [12,24,100];
  verticalChart: any[] = [];
  pieChart: any[] = [];
  sortType: string = "dateAdded"
  toggleAccuracy: boolean = false;
  accuracy: number = 0;

  constructor(private jobService: JobService,
              private contractorService: ContractorService,
              private statisticsService: StatisticsService,
              private router: Router) { }

  ngOnInit(): void {
    this.jobService.getJobRecordCount().subscribe(
      response => this.recordCount = response.body
    )
    this.getAllJobs();
    this.statisticsService.getCompaniesWithNumberOfOffers()
      .subscribe(response => {
        this.verticalChart = response.body;
      });
    this.statisticsService.mostLikedJobs()
      .subscribe(response => {
        this.pieChart = response.body;
      })
  }

  getRecommendedJobsIdsCBF(){
    this.jobService.getRecommendedJobsIdsCBF(this.currentUser.id).subscribe(
      response => this.jobService.getJobsByIds(8, response.body).subscribe(
        response => this.recommendedJobs = response.body
      )
    );
  }

  getAllJobs(): void{
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.sortType, this.currentFilter)
      .subscribe(response => this.jobs = response.body);
  }

  pageNavigations(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.sortType, this.currentFilter)
      .subscribe(response => this.jobs = response.body);

    return event
  }

  navigateToJobDetails(id: number){
    this.router.navigate(["job-list/details/", id]);
  }

  viewFilter(event: Filter) {
    this.currentFilter = event;
    this.jobService.getAllJobs(this.pageIndex, this.pageSize, this.sortType, this.currentFilter)
      .subscribe(response => this.jobs = response.body);
  }

  refresh(){
    window.location.reload();
  }

  rateRecommendation(relevanceMain: boolean, job:  Job) {
    let preference: Preference;
    this.jobService.getJobPreferenceForUser(this.currentUser.id, job.id)
      .subscribe(response => {preference = response.body;
                                    preference.relevanceMain = relevanceMain;
                                    this.jobService.savePreference(preference).subscribe()},
        error => {console.log(error.error);
          preference = new Preference(0, this.currentUser, job, null, false, relevanceMain, null);
          this.jobService.savePreference(preference).subscribe();});

    //this.refresh();
  }

  showAccuracy() {
    if(this.toggleAccuracy){
      this.toggleAccuracy = !this.toggleAccuracy;
      return;
    }
    this.jobService.computeMainRecommenderAccuracy(this.currentUser.id).subscribe(
      response => {this.accuracy = response.body;
        this.toggleAccuracy = !this.toggleAccuracy;}
    );
  }
}
