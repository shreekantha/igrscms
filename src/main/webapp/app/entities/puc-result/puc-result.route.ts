import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPucResult, PucResult } from 'app/shared/model/puc-result.model';
import { PucResultService } from './puc-result.service';
import { PucResultComponent } from './puc-result.component';
import { PucResultDetailComponent } from './puc-result-detail.component';
import { PucResultUpdateComponent } from './puc-result-update.component';

@Injectable({ providedIn: 'root' })
export class PucResultResolve implements Resolve<IPucResult> {
  constructor(private service: PucResultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPucResult> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pucResult: HttpResponse<PucResult>) => {
          if (pucResult.body) {
            return of(pucResult.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PucResult());
  }
}

export const pucResultRoute: Routes = [
  {
    path: '',
    component: PucResultComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.pucResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PucResultDetailComponent,
    resolve: {
      pucResult: PucResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.pucResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PucResultUpdateComponent,
    resolve: {
      pucResult: PucResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.pucResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PucResultUpdateComponent,
    resolve: {
      pucResult: PucResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.pucResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
