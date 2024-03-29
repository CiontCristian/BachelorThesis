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
import { JobDetailsComponent } from './job-details/job-details.component';
import { JobModifyComponent } from './job-modify/job-modify.component';
import { JobRemoveComponent } from './job-remove/job-remove.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import { AdminComponent } from './admin/admin.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatTableModule} from "@angular/material/table";
import {AgmCoreModule} from "@agm/core";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import { JobFilteringComponent } from './job-filtering/job-filtering.component';
import { UserModifyComponent } from './user-modify/user-modify.component';
import {NgxChartsModule} from "@swimlane/ngx-charts";
import {StatisticsService} from "./service/StatisticsService";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ToolbarComponent,
    RegisterComponent,
    JobListComponent,
    JobCardComponent,
    ContractorSaveComponent,
    ContractorManageComponent,
    JobSaveComponent,
    JobDetailsComponent,
    JobModifyComponent,
    JobRemoveComponent,
    AdminComponent,
    JobFilteringComponent,
    UserModifyComponent
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
        MatListModule,
        MatAutocompleteModule,
        MatGridListModule,
        MatExpansionModule,
        MatTableModule,
        AgmCoreModule.forRoot({
            apiKey: ''
        }),
        MatSlideToggleModule,
        NgxChartsModule,
        MatTooltipModule
    ],
  providers: [AccountService, JobService, ContractorService, StatisticsService],
  bootstrap: [AppComponent],
  entryComponents: [JobSaveComponent]
})
export class AppModule { }
