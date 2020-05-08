import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReviewService } from './review.service';
import { Review } from '../model/review';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book-review-form',
  templateUrl: './book-review-form.component.html',
  styleUrls: ['./book-review-form.component.css']
})
export class BookReviewFormComponent implements OnInit {
  @ViewChild('content') content: ElementRef;
  @ViewChild('actionButtons') actionButtons: ElementRef;
  @ViewChild('form') form: ElementRef;

  errorMessage : String;

  critique : String = '';
  rate : String = '';
  
  tags : Map<String, String> = new Map<String,String>();
  keys : String[] = [];
  values : String[] = [];
  
  bookReview : Review = new Review();

  bookReviewForm = new FormGroup({
    rate: new FormControl(''),
    critique: new FormControl('')
  });

  constructor(private _reviewService : ReviewService, private renderer: Renderer2,private router: Router) { }

  ngOnInit(): void {
  }

  generateTagInfo(event: Event) {
    const div1: HTMLDivElement = this.renderer.createElement('div');
    div1.innerHTML = "<input type=\"text\" name=\"tag\" id=\"tag\" [(ngModel)]=\"keys\" value=\"\" placeholder=\"Tag\">";
    this.renderer.addClass(div1,"col-6");
    this.renderer.addClass(div1,"col-12-xsmall");
    this.renderer.addClass(div1,"generated");
    const div2: HTMLDivElement = this.renderer.createElement('div');
    div2.innerHTML = "<input type=\"text\" name=\"info\" id=\"info\" value=\"\" placeholder=\"Informacija\">";
    this.renderer.addClass(div2,"col-6");
    this.renderer.addClass(div2,"col-12-xsmall");
    this.renderer.addClass(div2,"generated");

    this.renderer.insertBefore(this.content.nativeElement, div1, this.actionButtons.nativeElement);
    this.renderer.insertBefore(this.content.nativeElement, div2, this.actionButtons.nativeElement);
  }

  makeBookReview(){
    event.preventDefault();
    if(this.critique == '' || this.rate == ''){
      alert("Forma nije dobro popunjena.");
      return;
    }
    
    //pravljenje hesmape
    for(let el of this.content.nativeElement.querySelectorAll('#tag')){
      this.keys.push(el.value)
    }
    for(let el of this.content.nativeElement.querySelectorAll('#info')){
      this.values.push(el.value)
    }

    for(let i in this.keys){
      this.tags.set(this.keys[i], this.values[i]);
    }
    console.log(this.tags)
    
    const convMap = {};
    this.tags.forEach((val: string, key: string) => {
      convMap[key] = val;
    });
    
    this.bookReview.critique = this.critique;
    this.bookReview.rate = this.rate;
    this.bookReview.tags = convMap;
    //zasad zakucano
    this.bookReview.bookId = "2";
    console.log(this.bookReview)
    this._reviewService.postReview(this.bookReview).subscribe(
      error => this.errorMessage = <any>error
    );
    this.router.navigate(['']);
  }

  reset(event:Event){

    event.preventDefault();
    
    this.form.nativeElement.reset();

    let generatedElements = this.content.nativeElement.querySelectorAll('.generated');
    for(let el of generatedElements){
      el.remove();
    }
    this.keys.length = 0;
    this.values.length = 0;
    this.tags.clear();
  }
}
