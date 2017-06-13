import {FlowchartEntry} from './flowchart-entry.model';
import {Quarter} from './quarter.model';

export class Flowchart {
  id: number;
  name: string;
  userId: number;
  firstQuarter: Quarter;
  lastQuarter: Quarter;
  entries: FlowchartEntry[];
  isOfficial: boolean;
}

export class FlowchartCompact {
  id: number;
  name: string;
}
