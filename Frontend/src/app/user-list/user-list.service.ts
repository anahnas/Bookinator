import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import {User} from '../model/user';
import {Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class UserListService {
  private readonly allUsersURL: string;
  private readonly addUserURL: string;
  private readonly payMembershipURL: string;
  private readonly returnLoanURL: string;

  constructor(private http: HttpClient) {
    this.allUsersURL = 'http://localhost:8080/allUsers';
    this.addUserURL = 'http://localhost:8080/addUser';
    this.returnLoanURL = 'http://localhost:8080/bookLoan/return/';
    this.payMembershipURL = 'http://localhost:8080/payMembership/';

  }
  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.allUsersURL);
  }

  public saveUser(user: User): Observable<User> {
    return this.http.post<User>(this.addUserURL, user);
  }

  public payMembership(id:String): Observable<any> {
    return this.http.get(this.payMembershipURL+id)
    .pipe(catchError(this.handleError));
  }

  public returnLoan(id:String): Observable<any> {
    return this.http.put(this.returnLoanURL+id, null)
    .pipe(catchError(this.handleError));
  }
  
  private handleError(err: HttpErrorResponse){
    console.log(err.message);
    return throwError(err.message);
}
}

