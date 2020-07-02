import { Component, OnInit, Inject } from '@angular/core';
import { BookTag } from '../model/bookTag';
import { Book } from '../model/book';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-book-order-dialog',
  templateUrl: './book-order-dialog.component.html',
  styleUrls: ['./book-order-dialog.component.css']
})
export class BookOrderDialogComponent implements OnInit {

  tag: BookTag;
  tags: Set<BookTag>;
  book:Book = new Book();

  constructor(public dialogRef: MatDialogRef<BookOrderDialogComponent>,
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
