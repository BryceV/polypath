import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Quarter } from "../../models/quarter.model";
import { QuarterService} from "../../services/quarter.service"
import {QuarterView} from '../../models/quarter-view.model';
import {FlowchartEntry, FlowchartEntryCompact} from '../../models/flowchart-entry.model';
import {FlowchartService} from '../../services/flowchart.service';
import {FlowchartCompact} from '../../models/flowchart.model';

@Component({
  selector: 'app-quarter',
  templateUrl: './quarter.component.html',
  styleUrls: ['./quarter.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class QuarterComponent implements OnInit {

  @Input() quarterView : QuarterView;
  @Output() addCourseClicked = new EventEmitter();
  @Output() onCourseDropped = new EventEmitter();

  quarter: Quarter;
  drop: any = null;

  constructor(private quarterService : QuarterService,
              private flowchartService: FlowchartService) { }

  ngOnInit() {
    this.quarter = this.quarterView.quarter;
  }

  onAddCourseClicked() {
    this.addCourseClicked.emit(this.quarter);
  }

  onDropSuccess(event: any) {
    this.onCourseDropped.emit({entry: event.dragData, newQuarterId: this.quarter.id});
  }

  onDragEnter(event: any) {
  }
  onDragOver(event: any) {
  }
  onDragLeave(event: any) {
  }
}

