import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {JobService} from "../service/JobService";
import {Job} from "../model/Job";
import {StatisticsService} from "../service/StatisticsService";
import {AccountService} from "../service/AccountService";
import {ContractorService} from "../service/ContractorService";
import {Contractor} from "../model/Contractor";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  currentUser: User;
  jobs: Job[] = [];
  users: User[] = [];
  contractors: Contractor[] = [];

  constructor( private jobService: JobService,
               private accountService: AccountService,
               private contractorService: ContractorService) {}

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"))
    this.getUsers();
    this.getContractors();
  }

  getUsers() {
    this.accountService.getAllUsers()
      .subscribe(response => {this.users = response.body});
  }

  deleteUser(id: number){
    this.accountService.removeUser(id)
      .subscribe(response => console.log("User Removed!"));
    this.refresh();
  }

  getContractors() {
    this.contractorService.getAllContractors()
      .subscribe(response => {this.contractors = response.body});
  }

  deleteContractor(id: number){
    this.contractorService.removeContractor(id)
      .subscribe(response => console.log("Contractor removed!"));
    this.refresh();
  }

  getDatasetJobs() {
    this.jobService.getJobsFromDataset()
      .subscribe(response => {this.jobs = response.body;
          console.log(this.jobs);
          this.jobService.saveJobs(this.jobs).subscribe()
        },
        error => console.log(error.error))
  }

  refresh(){
    window.location.reload();
  }

}
