import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { SearchComponent } from './search/search.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { HeadingComponent } from './heading/heading.component';
import { SearchService } from './search/search.service';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { BookReviewFormComponent } from './book-review-form/book-review-form.component';
import { BookInfoDialogComponent } from './book-info-dialog/book-info-dialog.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDialogModule} from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    WelcomeComponent,
    HeadingComponent,
    LoginComponent,
    RegistrationComponent,
    BookReviewFormComponent,
    BookInfoDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDialogModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: 'search', component: SearchComponent},
      { path: 'login', component: LoginComponent},
      { path: 'registration', component: RegistrationComponent},
      { path: 'review', component: BookReviewFormComponent},
      { path: 'welcome', component: WelcomeComponent},
      { path: '', redirectTo:'welcome', pathMatch: 'full' },
      { path: '**', redirectTo:'welcome', pathMatch: 'full' }
    ])
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  providers: [BookInfoDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
