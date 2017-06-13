import { Injectable }               from '@angular/core';
import { Http }                     from '@angular/http';

import { Observable }               from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { Course }                   from '../models/course.model';
import {isNullOrUndefined} from 'util';
import {CourseService} from './course.service';

@Injectable()
export class CourseSearchService {
  COURSE_DATA_KEY= "COURSE_DATA";
  // courses: Map<number, Course>;
  courses: Course[];

  constructor( private http: Http,
                 private courseService: CourseService) {}

  initCourseData() {
    // this.courses = new Map<number, Course>(JSON.parse(localStorage.getItem(this.COURSE_DATA_KEY)));

    localStorage.removeItem(this.COURSE_DATA_KEY);
    this.courses = JSON.parse(localStorage.getItem(this.COURSE_DATA_KEY));

    if (isNullOrUndefined(this.courses) || Object.keys(this.courses).length === 0) {
      this.courseService.getCourses()
        .then((data) => {
          console.log("setting courses");
          // let coursesMap = new Map();
          // for (let course of courses) {
          //   coursesMap.set(course.id, course);
          // }
          // localStorage.setItem(this.COURSE_DATA_KEY, JSON.stringify(Array.from(coursesMap.entries())));
          localStorage.setItem(this.COURSE_DATA_KEY, JSON.stringify(data));
          this.courses = data;
      });
    }
  }

  getCourses() {
    console.log("get courses");
    return this.courses;
  }

  search(term: string): Observable<Course[]> {
    return this.http
      .get(`/api/courses?term=${term}`)
      .map(response => response.json() as Course[]);
  }
}
