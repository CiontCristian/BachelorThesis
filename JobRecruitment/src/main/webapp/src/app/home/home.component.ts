import { Component, OnInit } from '@angular/core';
import {Job} from "../model/Job";
import {PageEvent} from "@angular/material/paginator";
import {JobService} from "../service/JobService";
import {ContractorService} from "../service/ContractorService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  ngOnInit(): void {
  }

}
