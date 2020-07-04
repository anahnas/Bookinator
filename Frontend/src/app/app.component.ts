import { Component, OnInit } from '@angular/core';
import { SearchService } from './search/search.service';
import { ReviewService } from './book-review-form/review.service';
import { LoginService } from './login/login.service';
import { User } from './model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserListService } from './user-list/user-list.service';
import { TagListService } from './tag-list/tag-list.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [SearchService, ReviewService, LoginService, UserListService, TagListService]
})
export class AppComponent implements OnInit {


  title = 'Bookinator';
  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
  }

}

