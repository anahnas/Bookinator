import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { User } from '../model/user';
import { RegistrationService } from './registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [RegistrationService]
})
export class RegistrationComponent implements OnInit {
  registrationForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    passwordRepeat: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    email: new FormControl(''),
  });

  user:User;
  constructor(private _registrationService : RegistrationService, private router: Router) { }

  ngOnInit(): void {
  }

  register(){
    event.preventDefault();
    if(this.registrationForm.get('password').value != this.registrationForm.get('passwordRepeat').value){
      alert("Passwords dont match!");
      return;
    }
    this.user = this.registrationForm.value;
    this._registrationService.register(this.user).subscribe(
      user => {
        this.router.navigate(['/login']);
      },
      error => alert("Error occured")
    );
  }
}
