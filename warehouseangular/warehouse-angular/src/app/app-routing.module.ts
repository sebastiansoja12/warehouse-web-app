import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './auth/signup/signup.component';
import { LoginComponent } from './auth/login/login.component';

import { AuthGuard } from './auth/auth.guard';
import {HomeComponent} from './home/home.component';
import {RouteListComponent} from './route-list/route-list.component';
import {RouteViewComponent} from './route-view/route-view.component';
import {RouteFormComponent} from './route-find/route-form.component';
import {ParcelAddComponent} from './parcel-add/parcel-add.component';
import {RouteGetComponent} from './route-get/route-get.component';
import {RouteDeleteComponent} from './route-delete/route-delete.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {DepotAllComponent} from './depot-all/depot-all.component';
import {AdministratorComponent} from './administrator/administrator.component';

const routes: Routes = [
  { path: 'sign-up', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent },
  { path: 'routes/all', component: RouteListComponent },
  { path: 'route/parcelCode/:id', component: RouteViewComponent },
  { path: 'route/find', component: RouteFormComponent },
  { path: 'parcel/add', component: ParcelAddComponent },
  { path: 'route/add', component: RouteGetComponent },
  { path: 'route/delete', component: RouteDeleteComponent },
  { path: 'user/profile', component: UserProfileComponent },
  { path: 'depots/all', component: DepotAllComponent },
  { path: 'admin/routes/all/:id', component: AdministratorComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
