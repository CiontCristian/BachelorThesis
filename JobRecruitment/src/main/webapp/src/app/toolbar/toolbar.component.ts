import { Component, OnInit } from '@angular/core';
import {User} from "../model/User";
import {AccountService} from "../service/AccountService";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  public currentUser : User;

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
  }

  logout() : void{
    this.accountService.logout();
    this.ngOnInit();
  }

}
