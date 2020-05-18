import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { SearchService } from './search.service';
import { MatDialog } from '@angular/material/dialog';
import { Book } from '../model/book';
import { BookInfoDialogComponent } from '../book-info-dialog/book-info-dialog.component';
import { FormGroup, FormControl } from '@angular/forms';
import { BookDTO } from '../model/bookDTO';
import { SearchRequest } from '../model/searchRequest';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  genres : any[];
  styles : any[];
  likovi : String[] = [];
  bookRequest : Book = new Book();
  books : Book [] = [];
  temp:Book;
  selectedBook:Book;
  errorMessage : string;

  bookSearchForm = new FormGroup({
    name: new FormControl(''),
    author: new FormControl(''),
    periodSet: new FormControl(''),
    targetAgeGroup: new FormControl(''),
    yearPublished: new FormControl(''), 
    publisher: new FormControl(''),
    characters: new FormControl(''), 
    lesson: new FormControl(''),
    motives: new FormControl(''),
    genre: new FormControl(''),
    style: new FormControl(''),
    available: new FormControl('')
  });

  constructor(private _searchService : SearchService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.genres = this._searchService.getGenres();
    this.styles = this._searchService.getStyles();
  }

  dodajLika() : void {
    event.preventDefault();
    var node = document.createElement("LI"); 
    var inputValue = (<HTMLInputElement>document.getElementById("lik")).value;       
    if(inputValue == ""){
      return;
    }         
    var textnode = document.createTextNode(inputValue);         
    node.appendChild(textnode);                              
    document.getElementById("likovi").appendChild(node); 
    this.likovi.push(inputValue)    
  }

  makeSearchRequest(){
    event.preventDefault();
    this.books = [];
    
    console.log(this.bookSearchForm.value)
    //this.bookRequest = this.bookSearchForm.value;
    //this.bookRequest.characters = this.likovi;

    const searchRequest = new SearchRequest();
    searchRequest.searchCriteria = {};
    Object.keys(this.bookSearchForm.controls).forEach(key => {
      if(this.bookSearchForm.get(key).value!="" && this.bookSearchForm.get(key).value!=null)
      searchRequest.searchCriteria[key] = this.bookSearchForm.get(key).value;
    });
    for(let lik of this.likovi){
      searchRequest.searchCriteria["character"] = lik;
    }
    console.log(searchRequest)

    this._searchService.getFilteredBooks(searchRequest).subscribe(
      books => {
        for(let b of books){
          this.temp = new Book();
          this.temp.match = b.match;
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

    // dialogRef.afterClosed().subscribe(result => {
    //   console.log('The dialog was closed');
    //   this.message.message = result.message;
    //   this.message.reciever=result.user
    //   this.message.author=this.loginservice.loggedInUser
    //   this.send()
    // });
  }
}
