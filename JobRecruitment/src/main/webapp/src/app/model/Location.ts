import {BaseEntity} from "./BaseEntity";
import {User} from "./User";

export class Location extends BaseEntity{
  address : string;
  city : string;
  country : string;


  constructor(id: number, address: string, city: string, country: string) {
    super(id);
    this.address = address;
    this.city = city;
    this.country = country;
  }
}
