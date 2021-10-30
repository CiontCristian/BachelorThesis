import {BaseEntity} from "./BaseEntity";
import {Permission} from "./Permission";
import {Background} from "./Background";
import {Location} from "./Location";
import {Contractor} from "./Contractor";

export class User extends BaseEntity{
  password : string;
  email : string;
  firstName : string;
  lastName : string;
  dateOfBirth : Date;
  gender : string;
  background: Background;
  permission : Permission;
  location: Location;


  constructor(id: number, password: string, email: string, firstName: string, lastName: string, dateOfBirth: Date, gender: string, background: Background, permission: Permission, location: Location) {
    super(id);
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.background = background;
    this.permission = permission;
    this.location = location;
  }
}
