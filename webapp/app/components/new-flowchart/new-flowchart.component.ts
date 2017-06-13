import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';

import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';
import {Flowchart, FlowchartCompact} from '../../models/flowchart.model';
import {FlowchartService} from '../../services/flowchart.service';
import {FlowchartSearchService} from '../../services/flowchart-search.service';
import {MdDialogRef} from '@angular/material';

@Component({
  selector: 'app-new-flowchart',
  templateUrl: './new-flowchart.component.html',
  styleUrls: ['./new-flowchart.component.css']
})
export class NewFlowchartComponent implements OnInit {
  flowcharts: FlowchartCompact[];
  loading: boolean;

  selectedFlowchartId: number;

  constructor(public dialogRef: MdDialogRef<NewFlowchartComponent>,
              private flowchartService: FlowchartService) {
  }

  ngOnInit() {
    this.loading = true;
    this.flowchartService.getAllOfficialFlowcharts().then(flowcharts => {
      this.loading = false;
      this.flowcharts = flowcharts;
    }).catch((e) => {
      this.loading = false;
    });
  }

}
