import { Component, OnInit, Inject } from '@angular/core';
import { Book } from '../model/book';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-book-info-dialog',
  templateUrl: './book-info-dialog.component.html',
  styleUrls: ['./book-info-dialog.component.css']
})
export class BookInfoDialogComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<BookInfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Book) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
