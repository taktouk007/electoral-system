import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserApp, UserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';
import { UserAppComponent } from './user-app.component';
import { UserAppDetailComponent } from './user-app-detail.component';
import { UserAppUpdateComponent } from './user-app-update.component';

@Injectable({ providedIn: 'root' })
export class UserAppResolve implements Resolve<IUserApp> {
  constructor(private service: UserAppService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserApp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userApp: HttpResponse<UserApp>) => {
          if (userApp.body) {
            return of(userApp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserApp());
  }
}

export const userAppRoute: Routes = [
  {
    path: '',
    component: UserAppComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.userApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserAppDetailComponent,
    resolve: {
      userApp: UserAppResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.userApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserAppUpdateComponent,
    resolve: {
      userApp: UserAppResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.userApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserAppUpdateComponent,
    resolve: {
      userApp: UserAppResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.userApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
