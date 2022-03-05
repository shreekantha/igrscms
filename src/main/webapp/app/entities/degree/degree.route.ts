import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDegree, Degree } from 'app/shared/model/degree.model';
import { DegreeService } from './degree.service';
import { DegreeComponent } from './degree.component';
import { DegreeDetailComponent } from './degree-detail.component';
import { DegreeUpdateComponent } from './degree-update.component';

@Injectable({ providedIn: 'root' })
export class DegreeResolve implements Resolve<IDegree> {
  constructor(private service: DegreeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDegree> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((degree: HttpResponse<Degree>) => {
          if (degree.body) {
            return of(degree.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Degree());
  }
}

export const degreeRoute: Routes = [
  {
    path: '',
    component: DegreeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.degree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DegreeDetailComponent,
    resolve: {
      degree: DegreeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.degree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DegreeUpdateComponent,
    resolve: {
      degree: DegreeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.degree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DegreeUpdateComponent,
    resolve: {
      degree: DegreeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.degree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
