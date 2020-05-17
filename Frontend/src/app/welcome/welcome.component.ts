import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { GlobalPosition, InsidePlacement, Toppy, ToppyControl, RelativePosition, OutsidePlacement } from 'toppy';
import { WelcomeSpeechComponent } from '../virtual-assistant/welcome-speech/welcome-speech.component';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  _toppyControl2: ToppyControl;
  _toppyControlSpeech:ToppyControl;

  constructor(private toppy: Toppy) { }

  ngOnInit(): void {

    this._toppyControl2 = this.toppy
      .position(
        new GlobalPosition({
          placement: InsidePlacement.BOTTOM_RIGHT,
          width: '150px',
          height: '150px'
        })
      )
      .config({
        closeOnEsc: true,
        closeOnDocClick: false
      })
      .content('<img src="./assets/images/robot.png" width="100%"/>', { hasHTML: true })
      .create();
      this.openImage()

      if(localStorage.getItem("loggedIn")==null){

        this._toppyControlSpeech = this.toppy
        .position(
          new GlobalPosition({
            placement: OutsidePlacement.BOTTOM_RIGHT,
            width: 'auto',
            height: 'auto',
            offset: 120
          })
        )
        .config({closeOnDocClick:false})
        .content(WelcomeSpeechComponent, { text1: 'Hello! My name is Bookinator',text2:'Log in and let me help you!' })
        .create()
        this._toppyControlSpeech.listen('t_compins').subscribe(comp => {
          console.log('component is ready!', comp); // returns component
        });
        this.openSpeech();
    }

      else {
        this._toppyControlSpeech = this.toppy
        .position(
          new GlobalPosition({
            placement: OutsidePlacement.BOTTOM_RIGHT,
            width: 'auto',
            height: 'auto',
            offset: 110,
          })
        )
        .config({closeOnDocClick:false})
        .content(WelcomeSpeechComponent, { text1:'Click the "Search" toolbar',text2:'and start searching!' })
        .create()
        this._toppyControlSpeech.listen('t_compins').subscribe(comp => {
          console.log('component is ready!', comp); // returns component
        });
        this.openSpeech();
      }
     

  }

  openImage() {
    this._toppyControl2.open();
}

openSpeech() {
  this._toppyControlSpeech.open();
}



}
