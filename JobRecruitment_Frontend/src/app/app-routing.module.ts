import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {JobListComponent} from "./job-list/job-list.component";
import {ContractorSaveComponent} from "./contractor-save/contractor-save.component";
import {ContractorManageComponent} from "./contractor-manage/contractor-manage.component";
import {JobDetailsComponent} from "./job-details/job-details.component";
import {AdminComponent} from "./admin/admin.component";


const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "", component: JobListComponent},
  {path: "job-list/details/:jobID", component: JobDetailsComponent},
  {path: "contractor-save", component: ContractorSaveComponent},
  {path: "contractor-manage", component: ContractorManageComponent},
  {path: "admin", component: AdminComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
