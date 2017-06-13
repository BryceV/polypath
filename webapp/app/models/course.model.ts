import {Department} from './department.model';
export class Course {
  id: number;
  title: string;
  number: string;
  description: string;
  department: Department;

  // Class methods are not working for me -- 'property not found on type'
  getFullName(){
    return `${this.department.prefix} ${this.number}: ${this.title}`
  }
}
