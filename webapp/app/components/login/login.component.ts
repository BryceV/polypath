import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {isNullOrUndefined} from 'util';
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  loading = false;
  invalidCredentials: boolean;

  data = {
    username: "",
    password: ""
  };

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) {  }

  ngOnInit() {
    this.userService.logout()
      .then(() => {
        console.log("User signed out");
      });
  }

  onSubmit() {
    this.loading = true;
    this.invalidCredentials = false;
    this.userService.login(this.data.username, this.data.password)
      .catch((error: any) => {
        this.loading = false;
        if (error.status === 401)
        {
          this.invalidCredentials = true;
        }
        return Observable.throw(new Error(error.status));
      })
      .subscribe(() => {
        this.loading = false;
        this.invalidCredentials = false;
        this.router.navigate(['/']);
      }
    )
  }
}
