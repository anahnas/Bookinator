import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import {  catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Book } from '../model/book';
import { BookDTO } from '../model/bookDTO';

@Injectable()
export class BooksService{

    private _bookHistoryUrl = 'http://localhost:8080/books/';
    private _wishlistUrl = 'http://localhost:8080/wishlist/';
    constructor(private _http: HttpClient){ }


    getWishlist(userId : String) : Observable<BookDTO[]>{
        return this._http.post<BookDTO[]>(this._wishlistUrl, userId).pipe(
                    catchError(this.handleError));
    }

    private handleError(err: HttpErrorResponse){
        console.log(err.message);
        return throwError(err.message);
    }
}