import { Component } from '@angular/core';
import { SearchService } from './search/search.service';
import { ReviewService } from './book-review-form/review.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [SearchService, ReviewService]
})
export class AppComponent {
  title = 'Bookinator';
}
