import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { HttpHeaders } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import {  catchError } from 'rxjs/operators';
import { Book } from '../model/book';

const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
      //'Authorization': 'my-auth-token'
    })
  };

@Injectable()
export class SearchService{

    private _bookSearchUrl = 'http://localhost:8080/search';
    private _booksUrl = 'http://localhost:8080/books';
    //http://localhost:8080/
    constructor(private _http: HttpClient){ }

    getGenres() : any[]{
        return[
            {"name":"Realizam"},
            {"name":"Fantasika"},
            {"name":"Kriminalisika"},
            {"name":"Romantizam"},
            {"name":"Horor"},
            {"name":"Drama"},
            {"name":"Komedija"},
            {"name":"Enciklopedistika"},
            {"name":"Istorijski romani"},
            {"name":"Ratni romani"},
            {"name":"Novele"}
        ]
        
    };
    getStyles() : any[]{
        return[
            {"name":"Antika"},
            {"name":"Srednjovekovna književnost"},
            {"name":"Renesansa"},
            {"name":"Barok"},
            {"name":"Romantizam"},
            {"name":"Moderna"},
            {"name":"Savremena književnost"},
            {"name":"Naučna literatura"}
        ]
        
    }

    getFilteredBooks(book : Book) : Observable<Book[]>{
        return this._http.post<Book[]>(this._bookSearchUrl, book, httpOptions).pipe(
                    catchError(this.handleError));
    }

    getBooks() : Observable<Book[]>{
        return this._http.get<Book[]>(this._booksUrl).pipe(
                    catchError(this.handleError));
    }

    private handleError(err: HttpErrorResponse){
        console.log(err.message);
        return Observable.throw(err.message);
    }
}