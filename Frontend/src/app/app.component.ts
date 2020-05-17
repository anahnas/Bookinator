import { Component, OnInit } from '@angular/core';
import { SearchService } from './search/search.service';
import { ReviewService } from './book-review-form/review.service';
import { LoginService } from './login/login.service';
import { User } from './model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserListService } from './user-list/user-list.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [SearchService, ReviewService, LoginService, UserListService]
})
export class AppComponent implements OnInit {


  title = 'Bookinator';
  /*private loggedInUsername: String;
  user: User;
  user1: User;

  constructor(private router: Router, private route: ActivatedRoute, private service: LoginService) {
    this.user = new User();
    this.user1 = new User();
  }*/
  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    /*const user = JSON.parse(localStorage.getItem('user'));
    this.loggedInUsername = user.username;*/
  }

  /*login() {
    this.service.login(this.user).subscribe(
      response => {
        localStorage.setItem('user', JSON.stringify(response.username));
        this.loggedInUsername = this.user.username;

      },
      err => {
        if(err.status === 400) {
          alert('no such username');
        }
      });
    }



    get employee() {
      if(this.loggedInUsername === 'admin') { //username po ulozi dobaviti, a ne ovako
        return true;
      } else {
        return false;
      }
    }*/

    /*get someonelogged() {
      if(localStorage.getItem('username') === null) {
        return false;
      } else {
        return true;
      }
    }*/

  }

