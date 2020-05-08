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
export class ReviewService{

    private _bookReviewUrl = 'http://localhost:8080/review';
   
    constructor(private _http: HttpClient){ }


    postReview(review : any) {
        return this._http.post(this._bookReviewUrl, review, httpOptions).pipe(
                    catchError(this.handleError));
    }

    private handleError(err: HttpErrorResponse){
        console.log(err.message);
        return Observable.throw(err.message);
    }
}