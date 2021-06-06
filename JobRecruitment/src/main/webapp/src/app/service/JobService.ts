import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {Job} from "../model/Job";
import {Preference} from "../model/Preference";
import {environment} from "../../environments/environment";
import {Filter} from "../model/Filter";

@Injectable()
export class JobService{
  private jobURL = environment.apiBaseUrl + '/job';
  private searchValue = new BehaviorSubject<string>("");
  private recommenderURL = environment.recommenderBaseUrl + '/recommender';

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  sendSearchValue(value: string){
    this.searchValue.next(value);
  }

  receiveSearchValue(): Observable<string>{
    return this.searchValue.asObservable();
  }

  getAllJobs(pageIndex: number, pageSize: number, sortType: string, criteria: Filter): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.post<Job[]>(this.jobURL + "/findAllJobs?pageIndex="+pageIndex+"&pageSize="+pageSize
      +"&sortType="+sortType, criteria, {observe: "response"});
  }

  getJobRecordCount(): Observable<HttpResponse<number>>
  {
    return this.httpClient.get<number>(this.jobURL + "/getJobRecordCount", {observe: "response"});
  }

  getJobsByIds(count:number, ids: number[]): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.get<Job[]>(this.jobURL + "/findJobsByIds?count="+count+"&ids="+ids, {observe: "response"});
  }

  saveJob(job: Job): Observable<HttpResponse<Job>>{
    return this.httpClient.post<Job>(this.jobURL + "/saveJob", job, {observe: "response"});
  }

  saveJobs(jobs: Job[]): Observable<HttpResponse<Job[]>>{
    return this.httpClient.post<Job[]>(this.jobURL + "/saveJobs", jobs, {observe: "response"});
  }

  modifyJob(job: Job): Observable<HttpResponse<Job>>{
    return this.httpClient.put<Job>(this.jobURL + "/modifyJob", job, {observe: "response"});
  }

  removeJob(id: number): Observable<HttpResponse<void>>{
    return this.httpClient.delete<void>(this.jobURL + "/removeJob/" + id, {observe: "response"});
  }

  getJob(id: number): Observable<HttpResponse<Job>>
  {
    return this.httpClient.get<Job>(this.jobURL + "/findJobById/" + id,
      {observe: "response"});
  }

  savePreference(preference: Preference): Observable<HttpResponse<Preference>>
  {
    return this.httpClient.post<Preference>(this.jobURL + "/savePreference", preference, {observe: "response"});
  }

  findJobsForContractor(id: number): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.get<Job[]>(this.jobURL + "/findJobsForContractor/" + id,
      {observe: "response"});
  }

  findJobsTitles(): Observable<HttpResponse<string[]>>
  {
    return this.httpClient.get<string[]>(this.jobURL + "/findJobsTitles",
      {observe: "response"});
  }

  findJobByTitle(title: string): Observable<HttpResponse<Job>>
  {
    return this.httpClient.get<Job>(this.jobURL + "/findJobByTitle?title=" + title,
      {observe: "response"})
  }

  getJobPreferenceForUser(userId: number, jobId: number): Observable<HttpResponse<Preference>>
  {
    return this.httpClient.get<Preference>(this.jobURL + "/getJobPreferenceForUser?userId="+userId+
    "&jobId="+jobId, {observe: "response"});
  }

  getRecommendedJobsIdsKNN(jobId: number, userId): Observable<HttpResponse<number[]>>
  {
    return this.httpClient.post<number[]>(this.recommenderURL + "/getRecommendedJobsIdsKNN", [jobId, userId], {observe: "response"});
  }

  getRecommendedJobsIdsCBF(id: number): Observable<HttpResponse<number[]>>
  {
    return this.httpClient.post<number[]>(this.recommenderURL + "/getRecommendedJobsIdsCBF", id, {observe: "response"});
  }

  getJobsFromDataset(): Observable<HttpResponse<Job[]>>
  {
    return this.httpClient.get<Job[]>(this.recommenderURL + "/getJobs", {observe: "response"});
  }

  getAvailableDevTypes(): Observable<HttpResponse<string[]>>{
    return this.httpClient.get<string[]>(this.jobURL + "/getAvailableDevTypes", {observe: "response"});
  }

  getAvailableTechs(): Observable<HttpResponse<string[]>>{
    return this.httpClient.get<string[]>(this.jobURL + "/getAvailableTechs", {observe: "response"});
  }

  computeSecondaryRecommenderAccuracy(userId: number): Observable<HttpResponse<number>>{
    return this.httpClient.get<number>(this.jobURL + "/computeSecondaryRecommenderAccuracy/"+userId, {observe: "response"});
  }

  computeMainRecommenderAccuracy(userId: number): Observable<HttpResponse<number>>{
    return this.httpClient.get<number>(this.jobURL + "/computeMainRecommenderAccuracy/"+userId, {observe: "response"});
  }
}
