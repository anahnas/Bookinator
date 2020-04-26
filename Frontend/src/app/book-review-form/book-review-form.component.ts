import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-book-review-form',
  templateUrl: './book-review-form.component.html',
  styleUrls: ['./book-review-form.component.css']
})
export class BookReviewFormComponent implements OnInit {
  @ViewChild('generatedTags') div: ElementRef;
  @ViewChild('generatedTags') content: ElementRef;

  constructor(private renderer: Renderer2) { }

  ngOnInit(): void {
  }

  generateTagInfo(event: Event) {
    /*  <div class="col-6 col-12-xsmall">
                            <input type="text" name="prezime" id="prezime" value="" placeholder="Prezime">
                        </div>
                        <div class="col-6 col-12-xsmall">
                            <input type="text" name="username" id="username" value="" placeholder="Username">
                        </div>*/
                        
    //const div0: HTMLParagraphElement = this.renderer.createElement('div');
    const div0: HTMLParagraphElement = this.div.nativeElement;
   
    this.renderer.insertBefore()
    //this.renderer.addClass(div0,"col-12");
    //this.renderer.addClass(div0,"col-12-xsmall");

    //const div1: HTMLDivElement = this.renderer.createElement('div');
    //div1.innerHTML = "<input type=\"text\" name=\"tag\" id=\"tag\" value=\"\" placeholder=\"Tag\">";
    //this.renderer.addClass(div1,"col-6");
    //this.renderer.addClass(div1,"col-12-xsmall");
    //const div2: HTMLDivElement = this.renderer.createElement('div');
    //div2.innerHTML = "<input type=\"text\" name=\"prezime\" id=\"info\" value=\"\" placeholder=\"Informacija\">";
    //this.renderer.addClass(div2,"col-6");
    //this.renderer.addClass(div2,"col-12-xsmall");
    

    //this.renderer.appendChild(div0, div1)
    //this.renderer.appendChild(div0, div2)


  }
}
