import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-book-review-form',
  templateUrl: './book-review-form.component.html',
  styleUrls: ['./book-review-form.component.css']
})
export class BookReviewFormComponent implements OnInit {
  @ViewChild('content') content: ElementRef;
  @ViewChild('actionButtons') actionButtons: ElementRef;
  @ViewChild('form') form: ElementRef;

  constructor(private renderer: Renderer2) { }

  ngOnInit(): void {
  }

  generateTagInfo(event: Event) {
    const div1: HTMLDivElement = this.renderer.createElement('div');
    div1.innerHTML = "<input type=\"text\" name=\"tag\" id=\"tag\" value=\"\" placeholder=\"Tag\">";
    this.renderer.addClass(div1,"col-6");
    this.renderer.addClass(div1,"col-12-xsmall");
    this.renderer.addClass(div1,"generated");
    const div2: HTMLDivElement = this.renderer.createElement('div');
    div2.innerHTML = "<input type=\"text\" name=\"prezime\" id=\"info\" value=\"\" placeholder=\"Informacija\">";
    this.renderer.addClass(div2,"col-6");
    this.renderer.addClass(div2,"col-12-xsmall");
    this.renderer.addClass(div2,"generated");

    this.renderer.insertBefore(this.content.nativeElement, div1, this.actionButtons.nativeElement);
    this.renderer.insertBefore(this.content.nativeElement, div2, this.actionButtons.nativeElement);
  }

  reset(event:Event){

    event.preventDefault();
    
    this.form.nativeElement.reset();

    let generatedElements = this.content.nativeElement.querySelectorAll('.generated');
    for(let el of generatedElements){
      el.remove();
    }
  }
}
