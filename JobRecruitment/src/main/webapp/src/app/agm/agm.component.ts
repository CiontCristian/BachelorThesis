import {Component, OnInit} from '@angular/core';


@Component({
  selector: 'app-agm',
  templateUrl: './agm.component.html',
  styleUrls: ['./agm.component.css']
})
export class AgmComponent implements OnInit {

  constructor() { }
  latCluj = 46.74898191760513;
  lngCluj = 23.629722860492784;
  lat: number;
  lng: number;
  locationChosen = false;

  ngOnInit(): void {
  }

  rad(x: number){
    return x * Math.PI / 180;
  };

  getDistance(lat1, lat2, lng1, lng2) {

    var R = 6378137; // Earth’s mean radius in meter
    var dLat = this.rad(lat2 - lat1);
    var dLong = this.rad(lng2 - lng1);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.rad(lat1)) * Math.cos(this.rad(lat2)) *
      Math.sin(dLong / 2) * Math.sin(dLong / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // returns the distance in meter
  };

  onChosenLocation(event) {
    console.log(event);

    this.lat = event.coords.lat;
    this.lng= event.coords.lng;
    this.locationChosen = true;
    console.log(this.getDistance(this.latCluj, this.lat, this.lngCluj, this.lng))
  }
}
