import { Injectable } from '@angular/core';
import { Course } from "../models/course.model"
import { Http } from "@angular/http";
import 'rxjs/add/operator/toPromise';
import {toPromise} from "rxjs/operator/toPromise";
import {isNullOrUndefined} from 'util';
import {isEmpty} from 'rxjs/operator/isEmpty';

@Injectable()
export class CourseService {

  constructor(private http : Http) { }

  getCourses(): Promise<Course[]> {
    return this.http.get("api/courses")
      .toPromise()
      .then(response => response.json() as Course[])
      .catch(this.handleError);
  }

  getCourse(id : number): Promise<Course> {
    return this.http.get(`api/courses/${id}`)
      .toPromise()
      .then(response => {
        return response.json() as Course;}
      )
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.log('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
