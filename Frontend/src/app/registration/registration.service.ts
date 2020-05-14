import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import {  catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable()
export class RegistrationService{

    private _registerUrl = 'http://localhost:8080/register';
    constructor(private _http: HttpClient){ }


    register(user : User) : Observable<User>{
        return this._http.post<User>(this._registerUrl, user).pipe(
                    catchError(this.handleError));
    }

    private handleError(err: HttpErrorResponse){
        console.log(err.message);
        return throwError(err.message);
    }
}