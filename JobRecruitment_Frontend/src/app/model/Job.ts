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
  dateAdded: Date;
  contractor: Contractor;


  constructor(id: number, title: string, description: string, jobType: string, remote: boolean, minExperience: string, minCompensation: number, devType: string, techs: string, availablePos: number, dateAdded: Date, contractor: Contractor) {
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
    this.dateAdded = dateAdded;
    this.contractor = contractor;
  }
}
