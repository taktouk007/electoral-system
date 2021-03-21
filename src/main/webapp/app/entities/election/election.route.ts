import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IElection, Election } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';
import { ElectionComponent } from './election.component';
import { ElectionDetailComponent } from './election-detail.component';
import { ElectionUpdateComponent } from './election-update.component';

@Injectable({ providedIn: 'root' })
export class ElectionResolve implements Resolve<IElection> {
  constructor(private service: ElectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IElection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((election: HttpResponse<Election>) => {
          if (election.body) {
            return of(election.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Election());
  }
}

export const electionRoute: Routes = [
  {
    path: '',
    component: ElectionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.election.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ElectionDetailComponent,
    resolve: {
      election: ElectionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.election.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ElectionUpdateComponent,
    resolve: {
      election: ElectionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.election.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ElectionUpdateComponent,
    resolve: {
      election: ElectionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.election.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
