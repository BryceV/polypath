import {FlowchartService} from './flowchart.service';
import {forwardRef, Inject, Injectable} from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/toPromise';
import {User} from '../models/user.model';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {isNullOrUndefined} from 'util';
import {RoleType} from '../models/role-type';

@Injectable()
export class UserService {
  currentUserSource = new BehaviorSubject(UserService.getCurrentUser());
  currentUser$ = this.currentUserSource.asObservable();

  constructor(private http: Http,
              // http://stackoverflow.com/questions/37997824/angular-2-di-error-exception-cant-resolve-all-parameters
              @Inject(forwardRef(() => FlowchartService)) private flowchartService: FlowchartService) {}

  static getCurrentUser(): User {
    return JSON.parse(localStorage.getItem('currentUser')) as User;
  }

  static isCurrentUserAdmin(): boolean {
    if (isNullOrUndefined(UserService.getCurrentUser())) {
      return false;
    }

    // comparing enums in typescript is a pain
    let user = UserService.getCurrentUser();
    let roles = user.roles;

    for (var i = 0; i < roles.length; i++) {
      if (roles[i].toString() === "CATALOG_ADMIN") {
        return true;
      }
    }
    return false;
  }

  getCurrentUser(): Observable<User> {
    return this.currentUser$;
  }

    verifyUser(): Observable<User> {
    // TODO Catch error if non-500, clear user from local storage
    console.log('Verify User');
    return this.http.get('api/users/me').map(response => {
      const user = response.json() as User;
      console.log(user.firstName + " " + user.lastName);

      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSource.next(user);
      console.log('Verify user return');
      return user;
    })
      .catch((err) => {
      console.log(`Error verifying user: ${err}`);
      return Observable.throw(new Error(err.status));
    });
  }

    login(username: string, password: string): Observable<any> {
    return this.http.post('/login', {username: username, password: password})
    .switchMap(() => this.verifyUser())
  }

  logout(): Promise<any> {
    return this.http.get('/signout').map(response => {
      console.log(`User has logged out.`);
      localStorage.removeItem('currentUser');
      this.flowchartService.clearData();
      this.currentUserSource.next(null);
    // TODO Emit an event, or somehow clear the flowchart object in the app component.
      return response.text();
    }).toPromise();
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
