import {BaseEntity} from "./BaseEntity";

export class FileProperties extends BaseEntity{
  name: string;
  type: string;
  data: Uint8Array;


  constructor(id: number, name: string, type: string, data: Uint8Array) {
    super(id);
    this.name = name;
    this.type = type;
    this.data = data;
  }
}
