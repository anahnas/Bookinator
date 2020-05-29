import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  firstName:String;
  lastName:String;
  username:String;
  email:String;
  
  loggedInUser: any;

  constructor() { }

  ngOnInit(): void {
    this.loggedInUser = JSON.parse(localStorage.getItem('loggedIn'));
    console.log(this.loggedInUser)
    this.firstName = this.loggedInUser.firstName;
    this.lastName = this.loggedInUser.lastName;
    this.username = this.loggedInUser.username;
    this.email = this.loggedInUser.email;
    
  }

}
