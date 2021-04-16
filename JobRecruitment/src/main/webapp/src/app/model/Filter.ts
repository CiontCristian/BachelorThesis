
export class Filter{
  jobType : string;
  remote: boolean;
  minExperience: string;
  minCompensation: number;
  devType: string;
  techs: string;
  availablePos: number;


  constructor(jobType: string, remote: boolean, minExperience: string, minCompensation: number, devType: string, techs: string, availablePos: number) {
    this.jobType = jobType;
    this.remote = remote;
    this.minExperience = minExperience;
    this.minCompensation = minCompensation;
    this.devType = devType;
    this.techs = techs;
    this.availablePos = availablePos;
  }
}
