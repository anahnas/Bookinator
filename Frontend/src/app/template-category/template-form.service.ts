import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {  catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { TemplateDRTDTO } from '../model/templateDRTDTO';

@Injectable()
export class TemplateService {
    private readonly _templateURL = 'http://localhost:8080/templates';

    
    constructor(private _http: HttpClient){ }

    setCategory(template : TemplateDRTDTO) {
        return this._http.post(this._templateURL, template);
    }
}