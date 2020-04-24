import { Component, OnInit } from '@angular/core';
import { SearchService } from './search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  genres : any[];
  styles : any[];
  likovi : String[];

  constructor(private _searchService : SearchService) { }

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
}
