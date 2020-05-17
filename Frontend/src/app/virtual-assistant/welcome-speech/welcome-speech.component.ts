import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-welcome-speech',
  templateUrl: './welcome-speech.component.html',
  styleUrls: ['./welcome-speech.component.css']
})
export class WelcomeSpeechComponent implements OnInit {

  //firstpage=true;
  close; // just declare
  text1:String
  text2:String

  constructor() { 
    // if (localStorage.getItem("loggedIn")==null){
    //   this.firstpage=true
    // }
  }

  ngOnInit(): void {
  }

  // nextPage(){
  //   this.firstpage=false
  // }

  dismiss() {
    this.close(); // auto binded
  }
}
