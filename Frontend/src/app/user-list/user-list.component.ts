import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../model/user';
import { first } from 'rxjs/operators';
import { UserListService} from './user-list.service';
@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers:[UserListService]

})
export class UserListComponent implements OnInit {
  user: User;
  users: User [] = [];
  createUser: FormGroup;

  constructor(private router: Router, private route: ActivatedRoute, private service: UserListService,
    private formBuilder: FormBuilder) {
    this.user = new User();
    this.users = [];
  }

  ngOnInit(): void {
    this.service.getUsers().subscribe(data => {
      this.users = data;
    });

    this.createForm();
  }

  payMembership(id:String){
    this.service.payMembership(id).subscribe( result =>{
        window.location.reload();
      },
      error => alert("Error.")
    );

  }

  onSubmit() {

    this.service.saveUser(this.user).subscribe(result => {
      alert('You have added an user.');
      this.router.navigate(['/userList']);
    });
    }


  private createForm() {
    this.createUser = this.formBuilder.group({
      firstname: [''],
      lastname: [''],

    });
  }
  get f() { return this.createUser.controls; }

}
