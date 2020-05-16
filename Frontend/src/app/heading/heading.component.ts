import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { User } from '../model/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-heading',
  templateUrl: './heading.component.html',
  styleUrls: ['./heading.component.css'],
  providers: [LoginService]
})
export class HeadingComponent implements OnInit {
  private loggedInUsername: String;
  user: User;
  user1: User;
  admin = true;
  firstEmail: string;
  firstPassword: string;

  constructor(private router: Router, private route: ActivatedRoute, private service: LoginService) {
    this.user = new User();
    this.user1 = new User();
  }

  ngOnInit(): void {
    this.loggedInUsername = "EMPLOYEE";
  }
    /*his.service.login(this.user).subscribe(
      response => {
        localStorage.setItem('username', JSON.stringify(response.username));
        this.loggedInUsername = this.user.username;


    });/*

    /*if(localStorage.getItem('username') == null) {
    const user = JSON.parse(localStorage.getItem('user'));

    this.loggedInUsername = user.username;
    }*/


  /*login() {
    this.service.login(this.user).subscribe(
      response => {
        localStorage.setItem('username', JSON.stringify(response.username));
        this.loggedInUsername = this.user.username;

      },
      err => {
        if(err.status === 400) {
          alert('no such username');
        }
      });
    }*/



    get employee() {
      if(this.loggedInUsername === 'EMPLOYEE') { //username po ulozi dobaviti, a ne ovako
        return true;
      } else {
        return false;
      }
    }

    get homepage() {
      if(location.pathname==="/welcome") {
        return true;
      }else {
        return false;
      }


    }

    get member() {
      if(this.loggedInUsername === 'guest') { //username po ulozi dobaviti, a ne ovako
        return true;
      } else {
        return false;
      }
    }

    get someonelogged() {
      if(localStorage.getItem('username') === 'null') {
        return false;
      } else {
        return true;
      }
    }



  logout(){

    localStorage.removeItem("loggedIn");
    this.router.navigate(['']);
  }

}
