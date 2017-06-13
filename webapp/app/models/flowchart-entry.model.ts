import {Course} from './course.model';
import {Quarter} from './quarter.model';
import {Flowchart} from './flowchart.model';
export class FlowchartEntry {
  id: number;
  course: Course;
  quarter: Quarter;
  color: string;

  static COLORS = ["white", "#b9e8ce", "#ffec8e", "#ffd2b8"];
  static nextColor(color: string): string {
    let i = FlowchartEntry.COLORS.indexOf(color) > -1 ? FlowchartEntry.COLORS.indexOf(color) : 0;
    return FlowchartEntry.COLORS[(i+1) % 4];
  }
}

export class FlowchartEntryCompact {
  flowchart_id: number;
  course_id: number;
  quarter_id: number;
  color: string;

  constructor(flowchart_id: number, course_id: number, quarter_id: number, color: string) {
    this.flowchart_id = flowchart_id;
    this.course_id = course_id;
    this.quarter_id = quarter_id;
    this.color = color;
  }

}
