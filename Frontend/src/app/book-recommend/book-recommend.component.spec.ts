import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookRecommendComponent } from './book-recommend.component';

describe('BookRecommendComponent', () => {
  let component: BookRecommendComponent;
  let fixture: ComponentFixture<BookRecommendComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookRecommendComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookRecommendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
