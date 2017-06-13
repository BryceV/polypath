import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {User} from '../../models/user.model';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarComponent implements OnInit {
  @Output() onLogoClick = new EventEmitter();
  @Output() onSignOut = new EventEmitter();
  @Input() user: User;

  constructor() {}

  ngOnInit() {}

  onSignOutClicked() {
    console.log("onSignOut");
    this.onSignOut.emit();
  }

  onLogoClicked() {
    if(this.user != null)
    {
      console.log('onLogoClicked');
      this.onLogoClick.emit();
    }
  }
}
