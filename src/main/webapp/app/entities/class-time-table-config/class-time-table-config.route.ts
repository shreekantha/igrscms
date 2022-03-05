import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClassTimeTableConfig, ClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';
import { ClassTimeTableConfigService } from './class-time-table-config.service';
import { ClassTimeTableConfigComponent } from './class-time-table-config.component';
import { ClassTimeTableConfigDetailComponent } from './class-time-table-config-detail.component';
import { ClassTimeTableConfigUpdateComponent } from './class-time-table-config-update.component';

@Injectable({ providedIn: 'root' })
export class ClassTimeTableConfigResolve implements Resolve<IClassTimeTableConfig> {
  constructor(private service: ClassTimeTableConfigService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassTimeTableConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classTimeTableConfig: HttpResponse<ClassTimeTableConfig>) => {
          if (classTimeTableConfig.body) {
            return of(classTimeTableConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassTimeTableConfig());
  }
}

export const classTimeTableConfigRoute: Routes = [
  {
    path: '',
    component: ClassTimeTableConfigComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTableConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassTimeTableConfigDetailComponent,
    resolve: {
      classTimeTableConfig: ClassTimeTableConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTableConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassTimeTableConfigUpdateComponent,
    resolve: {
      classTimeTableConfig: ClassTimeTableConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTableConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassTimeTableConfigUpdateComponent,
    resolve: {
      classTimeTableConfig: ClassTimeTableConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTableConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
