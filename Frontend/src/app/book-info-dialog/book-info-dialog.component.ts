import { Component, OnInit, Inject } from '@angular/core';
import { Book } from '../model/book';
import { BookTag } from '../model/bookTag';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
  selector: 'app-book-info-dialog',
  templateUrl: './book-info-dialog.component.html',
  styleUrls: ['./book-info-dialog.component.css']
})
export class BookInfoDialogComponent implements OnInit {

  tag: BookTag;
  tags: Set<BookTag>;
  book:Book = new Book();

  constructor(public dialogRef: MatDialogRef<BookInfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Book) { 
      this.book = data['book'];
    }

  ngOnInit(): void {
    this.tag = new BookTag();
    this.tags = new Set<BookTag>();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
