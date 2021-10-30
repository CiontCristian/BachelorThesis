import {BaseEntity} from "./BaseEntity";
import {User} from "./User";

export class Background extends BaseEntity{
  techs: string;
  experience: string;
  jobType: string;
  devType: string;
  remote: boolean;


  constructor(id: number, techs: string, experience: string, jobType: string, devType: string, remote: boolean) {
    super(id);
    this.techs = techs;
    this.experience = experience;
    this.jobType = jobType;
    this.devType = devType;
    this.remote = remote;
  }
}
