import {Component} from "@angular/core";
import {MdDialogRef} from "@angular/material";
/**
 * Created by miguel on 6/7/17.
 */
@Component({
  selector: 'flowchart-delete-dialog',
  templateUrl: './flowchart-delete.component.html',
  styleUrls: ['./flowchart-delete.component.css']
})
export class FlowchartDeleteComponent {
  constructor(public dialogRef: MdDialogRef<FlowchartDeleteComponent>) {}
}
