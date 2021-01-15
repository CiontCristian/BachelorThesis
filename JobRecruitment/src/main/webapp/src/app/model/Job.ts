import {BaseEntity} from "./BaseEntity";
import {Contractor} from "./Contractor";

export class Job extends BaseEntity{
  title : string;
  description : string;
  type : string;
  contractor : Contractor;


  constructor(id: number, title: string, description: string, type: string, contractor: Contractor) {
    super(id);
    this.title = title;
    this.description = description;
    this.type = type;
    this.contractor = contractor;
  }
}
