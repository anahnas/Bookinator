import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BookTag} from '../model/bookTag';
import {Tag} from '../model/tag';
import {Observable} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class TagListService {

  private readonly tagListUrl: string;
  private readonly reqTagListUrl: string;
  private readonly deleteTagUrl: string;
  private readonly acceptTagUrl: string;
  private readonly tagreqUrl: string;
  private readonly manageTagUrl: string;

  constructor(private http: HttpClient) {
    this.tagListUrl = 'http://localhost:8080/getTags';
    this.tagreqUrl = 'http://localhost:8080/getTagsPair';
    this.reqTagListUrl = 'http://localhost:8080/getRequestedTags';
    this.deleteTagUrl = 'http://localhost:8080/deleteTag';
    this.acceptTagUrl = 'http://localhost:8080/approveTag';
    this.manageTagUrl = 'http://localhost:8080/manageTag';

  }

  public getAllTags(): Observable<Set<BookTag>> {
    return this.http.get<Set<BookTag>>(this.tagListUrl);
  }

  public getAllReqTags(): Observable<Set<Tag>> {
    return this.http.get<Set<Tag>>(this.tagreqUrl);
  }

  public getRequestedTags(): Observable<Set<BookTag>> {
    return this.http.get<Set<BookTag>>(this.reqTagListUrl);
  }

  public deleteTag(tagName: String): Observable<any> {
    return this.http.post<any>( this.deleteTagUrl, tagName);
  }


  public approveTag(tagName: String): Observable<any> {
    return this.http.post<any>(this.acceptTagUrl, tagName);
  }



}
