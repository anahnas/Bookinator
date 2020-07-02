import { Component, OnInit } from '@angular/core';
import { Book } from '../model/book';
import { BooksService } from '../user-profile/books.service';
import { MatDialog } from '@angular/material/dialog';
import { BookOrderDialogComponent } from '../book-order-dialog/book-order-dialog.component';

@Component({
  selector: 'app-welcome-admin',
  templateUrl: './welcome-admin.component.html',
  styleUrls: ['./welcome-admin.component.css'],
  providers: [BooksService]
})
export class WelcomeAdminComponent implements OnInit {
  booksWishlist : Book [] = [];
  temp:Book;
  selectedBook:Book;
  errorMessage : string;

  constructor(private _booksService: BooksService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.getWishlist();
  }

  getWishlist(){
    this.booksWishlist = [];
    this._booksService.getRecommendations().subscribe(obj => {
      Object.keys(obj).forEach(key => {
        //this._postsArray.push({obj: {key: key, val : { key_in:obj[key].key, val_in :obj[key].val}}});
        console.log(key);
        this._booksService.getBook(key).subscribe( b => {
          this.temp = new Book();
          this.temp.match = b.match;
          this.temp.availableNo = b.availableNo;
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
          this.temp.timesWished = obj[key];
          this.booksWishlist.push(this.temp);
          });
      });
    });
   
  }

  onSelect(book: Book): void {
    this.selectedBook = book;
    this.openDialog();
  }

  openDialog(){
    const dialogRef = this.dialog.open(BookOrderDialogComponent, {
      width: '250px',
      data: {book: this.selectedBook}
    });

  }
}
