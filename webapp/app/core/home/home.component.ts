import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Flowchart} from '../../models/flowchart.model';
import {MdSidenav} from '@angular/material';
import {FlowchartService} from '../../services/flowchart.service';
import {NavbarService} from '../../services/navbar.service';
import {Subscription} from 'rxjs/Subscription';
import {FlowchartView} from '../../models/flowchart-view.model';
import {isNullOrUndefined} from 'util';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit, OnDestroy{
  currentFlowchartView$: Observable<FlowchartView>;
  navBarEvent$: Observable<any>;

  navBarEventSubscription: Subscription;

  constructor(private flowchartService: FlowchartService,
              private navbarService: NavbarService) {

    this.navBarEvent$ = this.navbarService.getNavbarEvent$();
  }


  ngOnInit(): void {
    this.currentFlowchartView$ = this.flowchartService.getCurrentFlowchart()
      .map((flowchart) => {
        if (isNullOrUndefined(flowchart)){
          return null;
        }
        return this.flowchartService.buildFlowchartView(flowchart);
      }).catch((err) => {
        console.log(`Error building flowchartView: ${err}`);
        return Observable.throw(new Error(err.status));
      });

    // toggle sidenav on navbar event.
    this.navBarEventSubscription = this.navBarEvent$.map(() => {
      //navbar events
    }).subscribe();
  }

  ngOnDestroy(): void {
    this.navBarEventSubscription.unsubscribe();
  }
}
