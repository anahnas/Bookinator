import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import {  catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable()
export class UserProfileService{

    private _whoamiUrl = 'http://localhost:8080/whoami';
    private _discountUrl = 'http://localhost:8080/discount'
    constructor(private _http: HttpClient){ }


    whoami(user : User) : Observable<User>{
        return this._http.post<User>(this._whoamiUrl, user).pipe(
                    catchError(this.handleError));
    }
    getDiscount() {
        return this._http.get(this._discountUrl).pipe(
            catchError(this.handleError));
    }


    private handleError(err: HttpErrorResponse){
        console.log(err.message);
        return throwError(err.message);
    }
}