import {BaseEntity} from "./BaseEntity";
import {Contractor} from "./Contractor";

export class Job extends BaseEntity{
  title : string;
  description : string;
  jobType : string;
  remote: boolean;
  minExperience: string;
  minCompensation: number;
  devType: string;
  techs: string;
  availablePos: number;


  constructor(id: number, title: string, description: string, jobType: string, remote: boolean, minExperience: string, minCompensation: number, devType: string, techs: string, availablePos: number) {
    super(id);
    this.title = title;
    this.description = description;
    this.jobType = jobType;
    this.remote = remote;
    this.minExperience = minExperience;
    this.minCompensation = minCompensation;
    this.devType = devType;
    this.techs = techs;
    this.availablePos = availablePos;
  }
}
