import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import { LoginComponent } from './login/login.component';
import {MatIconModule} from "@angular/material/icon";
import { ToolbarComponent } from './toolbar/toolbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { HomeComponent } from './home/home.component';
import {AccountService} from "./service/AccountService";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { RegisterComponent } from './register/register.component';
import {MatRadioModule} from "@angular/material/radio";
import { JobListComponent } from './job-list/job-list.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {JobService} from "./service/JobService";
import { JobCardComponent } from './job-card/job-card.component';
import {MatCardModule} from "@angular/material/card";
import {ContractorService} from "./service/ContractorService";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatStepperModule} from "@angular/material/stepper";
import { ContractorSaveComponent } from './contractor-save/contractor-save.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import { ContractorManageComponent } from './contractor-manage/contractor-manage.component';
import { JobSaveComponent } from './job-save/job-save.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatChipsModule} from "@angular/material/chips";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSelectModule} from "@angular/material/select";
import {MatSliderModule} from "@angular/material/slider";
import {MatListModule} from "@angular/material/list";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ToolbarComponent,
    HomeComponent,
    RegisterComponent,
    JobListComponent,
    JobCardComponent,
    ContractorSaveComponent,
    ContractorManageComponent,
    JobSaveComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatIconModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatRadioModule,
    MatPaginatorModule,
    MatCardModule,
    NgbModule,
    MatStepperModule,
    MatSidenavModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule,
    MatCheckboxModule,
    MatSelectModule,
    MatSliderModule,
    MatListModule
  ],
  providers: [AccountService, JobService, ContractorService],
  bootstrap: [AppComponent],
  entryComponents: [JobSaveComponent]
})
export class AppModule { }
