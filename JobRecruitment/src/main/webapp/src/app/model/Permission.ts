import {BaseEntity} from "./BaseEntity";
import {User} from "./User";

export class Permission extends BaseEntity{
  isClient : boolean;
  isCompany: boolean;
  isAdmin : boolean;


  constructor(id: number, isClient: boolean, isCompany: boolean, isAdmin: boolean) {
    super(id);
    this.isClient = isClient;
    this.isCompany = isCompany;
    this.isAdmin = isAdmin;
  }
}
