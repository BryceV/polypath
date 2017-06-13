import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { FlowchartComponent } from './components/flowchart/flowchart.component';
import { CourseComponent } from './components/course/course.component';
import { CourseSearchComponent } from './components/course-search/course-search.component';
import { QuarterComponent } from './components/quarter/quarter.component';
import { AppComponent } from './app.component';
import { FlowchartService } from './services/flowchart.service';
import { CourseService } from "./services/course.service";
import { CourseSearchService } from "./services/course-search.service";
import { QuarterService } from "./services/quarter.service";
import { LoginComponent } from './components/login/login.component';
import {UserService} from './services/user.service';
import {LoginService} from './services/login.service';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './guards/auth.guard';
import {NavbarService} from './services/navbar.service';
import {HomeComponent} from './core/home/home.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { NewFlowchartComponent } from './components/new-flowchart/new-flowchart.component';
import { FlowchartDeleteComponent } from './components/flowchart-delete/flowchart-delete.component';
import {FlowchartSearchService} from './services/flowchart-search.service';
import {DndModule} from 'ng2-dnd';
import {TruncateModule} from 'ng2-truncate';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full', canActivate: [AuthGuard] },
  { path: 'home',  component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' } // TODO Or we can display an ErrorComponent since this is technically a 404
];

@NgModule({
  declarations: [
    AppComponent,
    CourseComponent,
    FlowchartComponent,
    LoginComponent,
    QuarterComponent,
    HomeComponent,
    NewFlowchartComponent,
    FlowchartDeleteComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BrowserAnimationsModule,
    FormsModule,
    CoreModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(routes, {useHash: true}),
    DndModule.forRoot(),
    TruncateModule
  ],
  entryComponents: [
    CourseSearchComponent,
    NewFlowchartComponent,
    FlowchartDeleteComponent
  ],
  providers: [FlowchartService,
              FlowchartSearchService,
              LoginService,
              UserService,
              CourseService,
              CourseSearchService,
              QuarterService,
              NavbarService,
              AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
