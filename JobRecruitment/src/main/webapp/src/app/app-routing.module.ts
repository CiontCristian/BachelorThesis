import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {JobListComponent} from "./job-list/job-list.component";
import {ContractorSaveComponent} from "./contractor-save/contractor-save.component";
import {ContractorManageComponent} from "./contractor-manage/contractor-manage.component";


const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "job-list", component: JobListComponent},
  {path: "contractor-save", component: ContractorSaveComponent},
  {path: "contractor-manage", component: ContractorManageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
