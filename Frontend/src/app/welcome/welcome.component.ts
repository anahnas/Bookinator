import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { GlobalPosition, InsidePlacement, Toppy, ToppyControl } from 'toppy';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  _toppyControl2: ToppyControl;

  @ViewChild('modalTpl', { read: TemplateRef }) modalTpl: TemplateRef<any>;

  constructor(private toppy: Toppy) { }

  ngOnInit(): void {
    console.log("ulogovan je " + localStorage.getItem("loggedIn"))

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
        closeOnDocClick: true
      })
      .content('<img src="./assets/images/robot.png" width="100%"/>', { hasHTML: true })
      .create();
      this.openImage()
  }

  openImage() {
    this._toppyControl2.open();

}
}
