import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFigure, Figure } from 'app/shared/model/figure.model';
import { FigureService } from './figure.service';
import { FigureComponent } from './figure.component';
import { FigureDetailComponent } from './figure-detail.component';
import { FigureUpdateComponent } from './figure-update.component';

@Injectable({ providedIn: 'root' })
export class FigureResolve implements Resolve<IFigure> {
  constructor(private service: FigureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFigure> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((figure: HttpResponse<Figure>) => {
          if (figure.body) {
            return of(figure.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Figure());
  }
}

export const figureRoute: Routes = [
  {
    path: '',
    component: FigureComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.figure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FigureDetailComponent,
    resolve: {
      figure: FigureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.figure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FigureUpdateComponent,
    resolve: {
      figure: FigureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.figure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FigureUpdateComponent,
    resolve: {
      figure: FigureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'electoralSystemApp.figure.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
