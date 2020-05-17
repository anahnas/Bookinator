import { Component, OnInit } from '@angular/core';
import { BookTag } from '../model/bookTag';
import { ActivatedRoute, Router } from '@angular/router';
import { TagListService } from './tag-list.service';
import { Tag } from '../model/tag';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.css'],
  providers: [TagListService]
})
export class TagListComponent implements OnInit {

  bookTags: Set<BookTag>;
  reqbookTags: Set<BookTag>;
  reqTags: Set<Tag>;
  tagg: Tag;
  tag: BookTag;
  tagId: number;

  constructor(private router: Router, private route: ActivatedRoute, private service: TagListService) { }

  ngOnInit(): void {
    this.service.getAllTags().subscribe(data => {this.bookTags = data;
      this.service.getAllReqTags().subscribe(data1 => {this.reqTags = data1;
      });

    });


  }

  deleteTag(tagName: String) {
    this.service.deleteTag(tagName).subscribe(result => this.router.navigate(['/tagList']));
  }

  approveTag(tagName: String) {
    this.service.approveTag(tagName).subscribe(result => this.router.navigate(['/tagList']));
  }

}
