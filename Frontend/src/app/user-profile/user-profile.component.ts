import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Book } from '../model/book';
import { BookInfoDialogComponent } from '../book-info-dialog/book-info-dialog.component';
import { BooksService } from './books.service';
import { MatDialog } from '@angular/material/dialog';
import { UserProfileService } from './user-profile.service';
import { User } from '../model/user';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  providers: [BooksService, UserProfileService]
})
export class UserProfileComponent implements OnInit {

  firstName:String;
  lastName:String;
  username:String;
  email:String;
  discount:number;
  membership:number;
  cathegory:string;

  booksHistory : Book [] = [];
  bookLoans : any[] = [];
  booksWishlist : Book [] = [];
  temp:Book;
  selectedBook:Book;
  similarUser : User;

  errorMessage : string;

  loggedInUser: any;

  constructor(private _booksService: BooksService, private _userProfileService: UserProfileService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loggedInUser = JSON.parse(localStorage.getItem('loggedIn'));
    this.firstName = this.loggedInUser.firstName;
    this.lastName = this.loggedInUser.lastName;
    this.username = this.loggedInUser.username;
    this.email = this.loggedInUser.email;
    this.discount = this.loggedInUser.discount;
    this.membership = this.loggedInUser.membership;
    this.cathegory = this.loggedInUser.cathegory;
    this.getBookHistory();
    this.getWishlist();
    this.getSimilarUsers();
  }

  getSimilarUsers(){
    this._booksService.getSimilarWishlists(this.loggedInUser.id).subscribe(
      response => {
        console.log("similar users")
        console.log(response)
        this.similarUser = response;
        }
      );   
  }

  getBookHistory(){
    this._booksService.getBookLoans(this.loggedInUser.id).subscribe(
      response => {
        for(let loan of response)
          this.bookLoans.push(loan);
        }
      );

    

    this.booksHistory = [];

    this._booksService.getBookHistory(this.loggedInUser.id).subscribe(
      books => {
        for(let b of books){
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

          for(let loan of this.bookLoans){
            if(loan.returned)
              this.temp.returned = true;
            else
              this.temp.returned = false;
          }

          this.booksHistory.push(this.temp);
          
        }
      },
      error => this.errorMessage = <any>error
    );
  }


  getWishlist(){
    this.booksWishlist = [];
    
    this._booksService.getWishlist(this.loggedInUser.id).subscribe(
      books => {
        for(let b of books){
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
          this.booksWishlist.push(this.temp);
          
        
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
