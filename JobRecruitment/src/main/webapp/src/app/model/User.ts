import {BaseEntity} from "./BaseEntity";
import {Permission} from "./Permission";

export class User extends BaseEntity{
  username : string;
  password : string;
  email : string;
  firstName : string;
  lastName : string;
  dateOfBirth : Date;
  phoneNumber : string;
  gender : string;
  permission : Permission;


  constructor(id: number, username: string, password: string, email: string, firstName: string, lastName: string, dateOfBirth: Date, phoneNumber: string, gender: string, permission: Permission) {
    super(id);
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.gender = gender;
    this.permission = permission;
  }

}
