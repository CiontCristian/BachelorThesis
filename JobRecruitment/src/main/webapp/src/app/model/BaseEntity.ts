
export class BaseEntity {
  private id: number;

  constructor(id: number) {
    this.id = id;
  }

  getID(): number{
    return this.id;
  }

  setID(newID: number): void{
    this.id = newID;
  }
}
