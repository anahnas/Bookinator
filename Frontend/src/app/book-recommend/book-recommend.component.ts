import { Component, OnInit } from '@angular/core';
import { RecommendService } from './recommend.service';
import { Book } from '../model/book';
import { MatDialog } from '@angular/material/dialog';
import { BookInfoDialogComponent } from '../book-info-dialog/book-info-dialog.component';
import { BookRecommended } from '../model/BookRecommended';

@Component({
  selector: 'app-book-recommend',
  templateUrl: './book-recommend.component.html',
  styleUrls: ['./book-recommend.component.css']
})
export class BookRecommendComponent implements OnInit {

  books : BookRecommended[]=[];
  selectedBook: Book;
  temp:BookRecommended;
  errorMessage: any;

  constructor( private recommendService: RecommendService, public dialog: MatDialog ) { }

  ngOnInit(): void {
    this.recommendService.recommend().subscribe(
      books => {
        for(let b of books){
          this.temp = new BookRecommended();
          this.temp.recommended = b.recommended;
          for(let tag of b.tags){
            if(tag.tagKey == '2'){
              this.temp.name = tag.tagValue;
            }

            if(tag.tagKey == '1'){
              this.temp.author = tag.tagValue;
            }
            
            if(tag.tagKey == '3'){
              this.temp.description = tag.tagValue;
            }
          }
          this.books.push(this.temp);
         
        }
                
      },
      error => this.errorMessage = <any>error
    );
  }

  onSelect(book: Book): void {
    this.selectedBook = book;
    this.openDialog();
  }

  openDialog(){
    const dialogRef = this.dialog.open(BookInfoDialogComponent, {
      width: '250px',
      
      data: {book: this.selectedBook}
    });
  }
}
