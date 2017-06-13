import { Injectable }               from '@angular/core';
import { Http }                     from '@angular/http';

import 'rxjs/add/operator/map';

import {isNullOrUndefined} from 'util';
import {FlowchartCompact} from '../models/flowchart.model';
import {FlowchartService} from './flowchart.service';

@Injectable()
export class FlowchartSearchService {
  OFFICIAL_FLOWCHART_DATA_KEY= "OFFICIAL_FLOWCHART_DATA";
  flowcharts: FlowchartCompact[];

  constructor( private http: Http,
               private flowchartService: FlowchartService) {}

  getFlowcharts() {
    return this.flowcharts;
  }

  initOfficialFlowchartsData() {
    this.flowcharts = JSON.parse(localStorage.getItem(this.OFFICIAL_FLOWCHART_DATA_KEY));

    if (isNullOrUndefined(this.flowcharts) || Object.keys(this.flowcharts).length === 0) {
      this.flowchartService.getAllOfficialFlowcharts().then((flowcharts) => {
        localStorage.setItem(this.OFFICIAL_FLOWCHART_DATA_KEY, JSON.stringify(flowcharts));
        this.flowcharts = flowcharts;
      });
    }
  }
}
