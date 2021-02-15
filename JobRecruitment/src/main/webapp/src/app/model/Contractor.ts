import {BaseEntity} from "./BaseEntity";
import {Location} from "./Location";
import {Job} from "./Job";
import {FileProperties} from "./FileProperties";
import {User} from "./User";

export class Contractor extends BaseEntity{
  name : string;
  description : string;
  nrOfEmployees: number;
  logo : FileProperties;
  location : Location;
  owner: User;


  constructor(id: number, name: string, description: string, nrOfEmployees: number, logo: FileProperties, location: Location, owner: User) {
    super(id);
    this.name = name;
    this.description = description;
    this.nrOfEmployees = nrOfEmployees;
    this.logo = logo;
    this.location = location;
    this.owner = owner;
  }
}
