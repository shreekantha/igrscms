import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAcademicCalendar, AcademicCalendar } from 'app/shared/model/academic-calendar.model';
import { AcademicCalendarService } from './academic-calendar.service';
import { AcademicCalendarComponent } from './academic-calendar.component';
import { AcademicCalendarDetailComponent } from './academic-calendar-detail.component';
import { AcademicCalendarUpdateComponent } from './academic-calendar-update.component';

@Injectable({ providedIn: 'root' })
export class AcademicCalendarResolve implements Resolve<IAcademicCalendar> {
  constructor(private service: AcademicCalendarService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcademicCalendar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((academicCalendar: HttpResponse<AcademicCalendar>) => {
          if (academicCalendar.body) {
            return of(academicCalendar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcademicCalendar());
  }
}

export const academicCalendarRoute: Routes = [
  {
    path: '',
    component: AcademicCalendarComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.academicCalendar.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcademicCalendarDetailComponent,
    resolve: {
      academicCalendar: AcademicCalendarResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.academicCalendar.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcademicCalendarUpdateComponent,
    resolve: {
      academicCalendar: AcademicCalendarResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.academicCalendar.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcademicCalendarUpdateComponent,
    resolve: {
      academicCalendar: AcademicCalendarResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.academicCalendar.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
