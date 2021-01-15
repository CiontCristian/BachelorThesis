import {BaseEntity} from "./BaseEntity";
import {Location} from "./Location";
import {Job} from "./Job";
import {FileProperties} from "./FileProperties";

export class Contractor extends BaseEntity{
  name : string;
  description : string;
  logo : FileProperties;
  location : Location;
  offers : Job[];


  constructor(id: number, name: string, description: string, logo: FileProperties, location: Location, offers: Job[]) {
    super(id);
    this.name = name;
    this.description = description;
    this.logo = logo;
    this.location = location;
    this.offers = offers;
  }
}
