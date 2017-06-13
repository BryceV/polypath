import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFlowchartComponent } from './new-flowchart.component';

describe('NewFlowchartComponent', () => {
  let component: NewFlowchartComponent;
  let fixture: ComponentFixture<NewFlowchartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewFlowchartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewFlowchartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
