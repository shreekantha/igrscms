import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISslcResult, SslcResult } from 'app/shared/model/sslc-result.model';
import { SslcResultService } from './sslc-result.service';
import { SslcResultComponent } from './sslc-result.component';
import { SslcResultDetailComponent } from './sslc-result-detail.component';
import { SslcResultUpdateComponent } from './sslc-result-update.component';

@Injectable({ providedIn: 'root' })
export class SslcResultResolve implements Resolve<ISslcResult> {
  constructor(private service: SslcResultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISslcResult> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sslcResult: HttpResponse<SslcResult>) => {
          if (sslcResult.body) {
            return of(sslcResult.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SslcResult());
  }
}

export const sslcResultRoute: Routes = [
  {
    path: '',
    component: SslcResultComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.sslcResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SslcResultDetailComponent,
    resolve: {
      sslcResult: SslcResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.sslcResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SslcResultUpdateComponent,
    resolve: {
      sslcResult: SslcResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.sslcResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SslcResultUpdateComponent,
    resolve: {
      sslcResult: SslcResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.sslcResult.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
