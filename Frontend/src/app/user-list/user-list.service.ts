import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/user';
import {Observable} from 'rxjs';

@Injectable()
export class UserListService {
  private readonly allUsersURL: string;
  private readonly addUserURL: string;


  constructor(private http: HttpClient) {
    this.allUsersURL = 'http://localhost:8080/allUsers';
    this.addUserURL = 'http://localhost:8080/addUser';

  }
  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.allUsersURL);
  }

  public saveUser(user: User): Observable<User> {
    return this.http.post<User>(this.addUserURL, user);
  }

}

