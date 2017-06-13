import { Injectable } from '@angular/core';
import { Quarter} from "../models/quarter.model";
import { Http } from "@angular/http";
import 'rxjs/add/operator/toPromise';
import {toPromise} from "rxjs/operator/toPromise";
import {isNullOrUndefined} from 'util';

@Injectable()
export class QuarterService {
  QUARTER_DATA_KEY= "QUARTER_DATA";
  quarters: Map<number, Quarter>;

  constructor(private http : Http) {}

  initQuarterData() {
    this.quarters = new Map<number, Quarter>(JSON.parse(localStorage.getItem(this.QUARTER_DATA_KEY)));

    if (isNullOrUndefined(this.quarters)  || Object.keys(this.quarters).length === 0) {
      this.getQuarters().then((quarters) => {
        let quartersMaps = new Map();
        for (let quarter of quarters) {
          quartersMaps.set(quarter.id, quarter);
        }
        localStorage.setItem(this.QUARTER_DATA_KEY, JSON.stringify(Array.from(quartersMaps.entries())));
        this.quarters = quartersMaps;
      });
    }
  }

  getQuarters(): Promise<Quarter[]> {
    return this.http.get("api/quarters")
      .toPromise()
      .then(response => response.json() as Quarter[])
      .catch(this.handleError);
  }

  getQuarter(id : number): Promise<Quarter> {
    return this.http.get(`api/quarters/${id}`)
      .toPromise()
      .then(response => {
        //console.log(response.json());
        return response.json() as Quarter;}
      )
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.log('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
