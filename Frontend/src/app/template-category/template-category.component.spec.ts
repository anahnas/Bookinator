import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateCategoryComponent } from './template-category.component';

describe('TemplateCategoryComponent', () => {
  let component: TemplateCategoryComponent;
  let fixture: ComponentFixture<TemplateCategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateCategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
