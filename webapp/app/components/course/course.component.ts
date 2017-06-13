import {ChangeDetectionStrategy, Component, Input, OnInit, Output} from '@angular/core';
import {FlowchartEntry, FlowchartEntryCompact} from '../../models/flowchart-entry.model';
import {FlowchartService} from '../../services/flowchart.service';
import { TruncateModule } from 'ng2-truncate';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CourseComponent implements OnInit {

  @Input() entry: FlowchartEntry;

  constructor(private flowchartService : FlowchartService) { }

  ngOnInit() {
  }

  onCourseRemove() {
    console.log(`OnCourseRemove: ${this.entry.id}`);
    this.flowchartService.deleteEntry(this.entry);
  }

  onColorChange() {
    this.entry.color = FlowchartEntry.nextColor(this.entry.color);
    console.log(`next color: ${this.entry.color}`);
    this.flowchartService.updateEntry(this.entry.id, new FlowchartEntryCompact(this.flowchartService.getActiveFlowchartId(), this.entry.course.id, this.entry.quarter.id, this.entry.color));
  }

  onClick() {
    console.log(JSON.stringify(this.entry));
  }
}
