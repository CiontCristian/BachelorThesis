import {BaseEntity} from "./BaseEntity";
import {User} from "./User";

export class Location extends BaseEntity{
  latitude: number;
  longitude: number;

  constructor(id: number, latitude: number, longitude: number) {
    super(id);
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
