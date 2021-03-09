
export class RecommenderInfo{
  user_id: number;
  algorithm: string;


  constructor(user_id: number, algorithm: string) {
    this.user_id = user_id;
    this.algorithm = algorithm;
  }
}
