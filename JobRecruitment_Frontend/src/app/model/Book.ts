import {BaseEntity} from "./BaseEntity";

export class Book extends BaseEntity{
  title: string;
  author: string;
  firstSentence: string;
  yearPublished: number;


  constructor(id: number, title: string, author: string, firstSentence: string, yearPublished: number) {
    super(id);
    this.title = title;
    this.author = author;
    this.firstSentence = firstSentence;
    this.yearPublished = yearPublished;
  }
}
