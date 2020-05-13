import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { User } from '../model/user';
import { LoginService } from './login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers:[LoginService]
})
export class LoginComponent implements OnInit {
  
  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  user:User;

  constructor(private _loginService : LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  login(){
    event.preventDefault();
    this.user = this.loginForm.value;
    this._loginService.login(this.user).subscribe(
      user => {
        this.user = user;
        localStorage.setItem("loggedIn", JSON.stringify(this.user));
        this.router.navigate(['']);
      },
      error => alert("Pogrešno korisničko ime ili lozinka.")
    );
  }
}
