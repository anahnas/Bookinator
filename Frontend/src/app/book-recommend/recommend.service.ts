import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginService } from '../login/login.service';
import { User } from '../model/user';
import { BookRecommendComponent } from './book-recommend.component';
import { bookRecommendDTO } from '../model/bookRecommendDTO';

@Injectable({
  providedIn: 'root'
})
export class RecommendService {

   httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  loggedInUser : User;

  constructor(private _http : HttpClient, private loginService : LoginService) { 
    this.loggedInUser = JSON.parse(localStorage.getItem("loggedIn"));
  }

  public recommend(){
    return this._http.get<bookRecommendDTO[]>('http://localhost:8080/recommend/'+this.loggedInUser.id, this.httpOptions)
  }
}
