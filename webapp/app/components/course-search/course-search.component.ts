import {Component, Inject, OnInit, Optional}            from '@angular/core';
import { Router }                       from '@angular/router';
import { Observable }                   from 'rxjs/Observable';
import { Subject }                      from 'rxjs/Subject';

import 'rxjs/add/observable/of';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';

import { CourseSearchService }          from '../../services/course-search.service';
import { Course }                       from '../../models/course.model';
import {MD_DIALOG_DATA, MdDialogRef} from '@angular/material';
import {Quarter} from '../../models/quarter.model';
import {FormControl} from '@angular/forms';
import {isNullOrUndefined} from 'util';
import {CourseService} from '../../services/course.service';

@Component({
    selector: 'app-course-search',
    templateUrl: './course-search.component.html',
    styleUrls: ['./course-search.component.css'],
    providers: [CourseSearchService]
})
export class CourseSearchComponent implements OnInit {
    courses: Course[];
    callingQuarter: Quarter;
    courseCtrl: FormControl;
    filteredCourses: any;
    loading: boolean = false;
    selectedCourse: Course;

    private searchTerms = new Subject<string>();

    constructor(
        public dialogRef: MdDialogRef<CourseSearchComponent>,
        @Optional() @Inject(MD_DIALOG_DATA) public data: Quarter,
        private courseSearchService: CourseSearchService,
        private courseService: CourseService,
        private router: Router) {

      this.callingQuarter = data;
      this.courseCtrl = new FormControl();
      this.filteredCourses = this.courseCtrl.valueChanges
        .startWith(null)
        .debounceTime(100)
        .distinctUntilChanged()
        .map(name => this.filterCourses(name));
    }

    ngOnInit(): void {
      this.courses = this.courseSearchService.courses || JSON.parse(localStorage.getItem(this.courseSearchService.COURSE_DATA_KEY));

      if (isNullOrUndefined(this.courses) || (this.courses).length === 0) {
        this.loading = true;
        this.courseService.getCourses()
          .then((data) => {
            this.loading = false;
            console.log('returned data');
            localStorage.setItem(this.courseSearchService.COURSE_DATA_KEY, JSON.stringify(data));
            this.courses = data;
          });
      }
        // this.courses = this.searchTerms
        //     .debounceTime(300)
        //     .distinctUntilChanged()
        //     .switchMap(term => term ? this.courseSearchService.search(term)
        //         : Observable.of<Course[]>([]))
        //     .catch(error => {
        //         console.log("Error getting courses.");
        //         console.log(error);
        //         return Observable.of<Course[]>([]);
        //     });
    }

    filterCourses(val: string){
      if(!isNullOrUndefined(val) && typeof val === 'object'){
        return;
      }

      this.selectedCourse = null;
      if (isNullOrUndefined(val) || val.length < 2) {
        return;
      }

      return val ? this.courses.filter(course => {
          let c = `${course.department.prefix} ${course.number} ${course.title}`.toLowerCase();
          return c.indexOf(val.toLowerCase()) > -1;
        }) : this.courses;
    }

    courseName(course: Course): string {
      return course ? `${course.department.prefix} ${course.number}: ${course.title}` : "";
    }

    onKeyUp(event: any){
      if (event.keyCode == 13 && !isNullOrUndefined(this.selectedCourse)) {
        // submit course
        console.log(`Submitting course: ${this.selectedCourse.id}`);
        this.dialogRef.close(this.selectedCourse);
      }
    }

    onSelectionChange(event: any, data: any){
      this.selectedCourse = data;
    }
}
