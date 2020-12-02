import {BaseEntity} from "./BaseEntity";

export class Permission extends BaseEntity{
  isClient : boolean;
  isAdmin : boolean;

  constructor(id: number, isClient: boolean, isAdmin: boolean) {
    super(id);
    this.isClient = isClient;
    this.isAdmin = isAdmin;
  }

}
