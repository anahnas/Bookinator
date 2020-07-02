import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookOrderDialogComponent } from './book-order-dialog.component';

describe('BookOrderDialogComponent', () => {
  let component: BookOrderDialogComponent;
  let fixture: ComponentFixture<BookOrderDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookOrderDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookOrderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
