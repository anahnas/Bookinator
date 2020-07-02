import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../model/user';
import { first } from 'rxjs/operators';
import { UserListService} from './user-list.service';
import { BooksService } from '../user-profile/books.service';
import { BookLoan } from '../model/bookLoan';
@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers:[UserListService, BooksService]

})
export class UserListComponent implements OnInit {
  user: User;
  users: User [] = [];
  createUser: FormGroup;

  constructor(private router: Router, private route: ActivatedRoute, private service: UserListService, 
    private bookService: BooksService, private formBuilder: FormBuilder) {
    this.user = new User();
    this.users = [];
  }

  ngOnInit(): void {

    this.service.getUsers().subscribe(data => {
      this.users = data;
      for(let u of this.users){
        if(u.userType != "EMPLOYEE"){
          this.bookService.getBookLoan(u.id).subscribe(bookLoan => {
            if(bookLoan === null || bookLoan === undefined)
              u.loan = '';
            else{
                for(let tag of bookLoan.tags){
                  if(tag.tagKey == '2'){
                    u.loan = tag.tagValue;
                  }
                }
      
            }
          });
        } else {
          u.loan = '';
        }
      }
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

  returnLoan(uId:String){
    this.service.returnLoan(uId).subscribe( result =>{
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
