import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { Flowchart } from '../../models/flowchart.model';
import { FlowchartService } from '../../services/flowchart.service'
import {User} from '../../models/user.model';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SideNavComponent implements OnInit {
  @Output() newFlowchart = new EventEmitter();
  @Output() flowchartSelected = new EventEmitter();
  @Output() sideNavClose = new EventEmitter();
  @Input() flowcharts: Flowchart[];
  @Input() selectedFlowchartId: number;
  @Input() currentUser: User;

  constructor(private flowchartService: FlowchartService) {}

  ngOnInit() {}

  onFlowchartSelected(id : number)
  {
    console.log(`onFlowchartSelected: ${id}`);
    this.flowchartSelected.emit();
    this.flowchartService.setCurrentFlowchartByIDInMap(id);
  }

  onSidebarClose() {
    console.log('onSideBarClose');
    this.sideNavClose.emit();
  }

  onNewFlowchart() {
    console.log('onNewFlowchart');
    this.newFlowchart.emit();
  }
}
