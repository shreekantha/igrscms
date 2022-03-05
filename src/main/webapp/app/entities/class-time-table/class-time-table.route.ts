import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClassTimeTable, ClassTimeTable } from 'app/shared/model/class-time-table.model';
import { ClassTimeTableService } from './class-time-table.service';
import { ClassTimeTableComponent } from './class-time-table.component';
import { ClassTimeTableDetailComponent } from './class-time-table-detail.component';
import { ClassTimeTableUpdateComponent } from './class-time-table-update.component';

@Injectable({ providedIn: 'root' })
export class ClassTimeTableResolve implements Resolve<IClassTimeTable> {
  constructor(private service: ClassTimeTableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassTimeTable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classTimeTable: HttpResponse<ClassTimeTable>) => {
          if (classTimeTable.body) {
            return of(classTimeTable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassTimeTable());
  }
}

export const classTimeTableRoute: Routes = [
  {
    path: '',
    component: ClassTimeTableComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassTimeTableDetailComponent,
    resolve: {
      classTimeTable: ClassTimeTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassTimeTableUpdateComponent,
    resolve: {
      classTimeTable: ClassTimeTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassTimeTableUpdateComponent,
    resolve: {
      classTimeTable: ClassTimeTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.classTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
