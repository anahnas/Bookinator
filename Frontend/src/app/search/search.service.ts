import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { HttpHeaders } from '@angular/common/http';
import { from, Observable, throwError } from 'rxjs';
import {  catchError } from 'rxjs/operators';
import { Book } from '../model/book';
import { BookDTO } from '../model/bookDTO';

const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
      //'Authorization': 'my-auth-token'
    })
  };

@Injectable()
export class SearchService{

    private _bookSearchUrl = 'http://localhost:8080/book';
    private _booksUrl = 'http://localhost:8080/books';
    constructor(private _http: HttpClient){ }

    getGenres() : any[]{
        return[
            {"name":"Realism"},
            {"name":"Fantasy"},
            {"name":"Crime"},
            {"name":"Romance"},
            {"name":"Horror"},
            {"name":"Drama"},
            {"name":"Comedy"},
            {"name":"Encyclopedia"},
            {"name":"History"},
            {"name":"War"},
            {"name":"Novel"}
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

    getFilteredBooks(searchRequest : any) : Observable<BookDTO[]>{
        return this._http.post<BookDTO[]>(this._bookSearchUrl, searchRequest, httpOptions).pipe(
                    catchError(this.handleError));
    }

    getBooks() : Observable<Book[]>{
        return this._http.get<Book[]>(this._booksUrl).pipe(
                    catchError(this.handleError));
    }

    private handleError(err: HttpErrorResponse){
        console.log(err.message);
        return throwError(err.message);
    }
}