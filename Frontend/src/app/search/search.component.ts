import { Component, OnInit } from '@angular/core';
import { SearchService } from './search.service';
import { MatDialog } from '@angular/material/dialog';
import { Book } from '../model/book';
import { BookInfoDialogComponent } from '../book-info-dialog/book-info-dialog.component';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  genres : any[];
  styles : any[];
  likovi : String[];
  selectedBook:Book

  constructor(private _searchService : SearchService,public dialog: MatDialog) { }

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
