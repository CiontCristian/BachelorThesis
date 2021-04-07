import {BaseEntity} from "./BaseEntity";
import {User} from "./User";
import {Job} from "./Job";

export class Preference extends BaseEntity{
  user: User;
  job: Job;
  interested: boolean;
  applied: boolean;


  constructor(id: number, user: User, job: Job, interested: boolean, applied: boolean) {
    super(id);
    this.user = user;
    this.job = job;
    this.interested = interested;
    this.applied = applied;
  }
}
