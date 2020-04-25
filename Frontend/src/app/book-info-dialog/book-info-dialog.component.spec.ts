import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookInfoDialogComponent } from './book-info-dialog.component';

describe('BookInfoDialogComponent', () => {
  let component: BookInfoDialogComponent;
  let fixture: ComponentFixture<BookInfoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookInfoDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
