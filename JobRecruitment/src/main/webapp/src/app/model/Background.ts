import {BaseEntity} from "./BaseEntity";

export class Background extends BaseEntity{
  formalEducation: string;
  experience: string;


  constructor(id: number, formalEducation: string, experience: string) {
    super(id);
    this.formalEducation = formalEducation;
    this.experience = experience;
  }
}
