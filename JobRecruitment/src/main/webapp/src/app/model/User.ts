import {BaseEntity} from "./BaseEntity";
import {Permission} from "./Permission";
import {Background} from "./Background";
import {Location} from "./Location";
import {Contractor} from "./Contractor";

export class User extends BaseEntity{
  username : string;
  password : string;
  email : string;
  firstName : string;
  lastName : string;
  dateOfBirth : Date;
  phoneNumber : string;
  gender : string;
  background: Background;
  permission : Permission;
  location: Location;
  company: Contractor;


  constructor(id: number, username: string, password: string, email: string, firstName: string, lastName: string, dateOfBirth: Date, phoneNumber: string, gender: string, background: Background, permission: Permission, location: Location, company: Contractor) {
    super(id);
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.gender = gender;
    this.background = background;
    this.permission = permission;
    this.location = location;
    this.company = company;
  }
}
