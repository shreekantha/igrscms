import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamTimeTable, ExamTimeTable } from 'app/shared/model/exam-time-table.model';
import { ExamTimeTableService } from './exam-time-table.service';
import { ExamTimeTableComponent } from './exam-time-table.component';
import { ExamTimeTableDetailComponent } from './exam-time-table-detail.component';
import { ExamTimeTableUpdateComponent } from './exam-time-table-update.component';

@Injectable({ providedIn: 'root' })
export class ExamTimeTableResolve implements Resolve<IExamTimeTable> {
  constructor(private service: ExamTimeTableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamTimeTable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examTimeTable: HttpResponse<ExamTimeTable>) => {
          if (examTimeTable.body) {
            return of(examTimeTable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamTimeTable());
  }
}

export const examTimeTableRoute: Routes = [
  {
    path: '',
    component: ExamTimeTableComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.examTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExamTimeTableDetailComponent,
    resolve: {
      examTimeTable: ExamTimeTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.examTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExamTimeTableUpdateComponent,
    resolve: {
      examTimeTable: ExamTimeTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.examTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExamTimeTableUpdateComponent,
    resolve: {
      examTimeTable: ExamTimeTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.examTimeTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
