import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomeSpeechComponent } from './welcome-speech.component';

describe('WelcomeSpeechComponent', () => {
  let component: WelcomeSpeechComponent;
  let fixture: ComponentFixture<WelcomeSpeechComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WelcomeSpeechComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomeSpeechComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
