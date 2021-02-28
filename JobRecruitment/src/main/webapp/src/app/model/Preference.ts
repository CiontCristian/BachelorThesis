import {BaseEntity} from "./BaseEntity";
import {User} from "./User";
import {Job} from "./Job";

export class Preference extends BaseEntity{
  user: User;
  job: Job;
  isInterested: boolean;
  rating: number;


  constructor(id: number, user: User, job: Job, isInterested: boolean, rating: number) {
    super(id);
    this.user = user;
    this.job = job;
    this.isInterested = isInterested;
    this.rating = rating;
  }
}
