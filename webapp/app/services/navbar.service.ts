import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Subject} from 'rxjs/Subject';
@Injectable ()
export class NavbarService {

  private navBarSource = new Subject();
  private navBarEvent$ = this.navBarSource.asObservable();

  constructor() {}

  getNavbarEvent$() {
    return this.navBarEvent$
  }

  onSideNavToggle() {
    console.log('onSideNavToggle');
    // We could pass state here if we want, but just always toggling right now.
    this.navBarSource.next({});
  }
}
