import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStudentProfile, StudentProfile } from 'app/shared/model/student-profile.model';
import { StudentProfileService } from './student-profile.service';
import { StudentProfileComponent } from './student-profile.component';
import { StudentProfileDetailComponent } from './student-profile-detail.component';
import { StudentProfileUpdateComponent } from './student-profile-update.component';

@Injectable({ providedIn: 'root' })
export class StudentProfileResolve implements Resolve<IStudentProfile> {
  constructor(private service: StudentProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStudentProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((studentProfile: HttpResponse<StudentProfile>) => {
          if (studentProfile.body) {
            return of(studentProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StudentProfile());
  }
}

export const studentProfileRoute: Routes = [
  {
    path: '',
    component: StudentProfileComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.studentProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StudentProfileDetailComponent,
    resolve: {
      studentProfile: StudentProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.studentProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StudentProfileUpdateComponent,
    resolve: {
      studentProfile: StudentProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.studentProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StudentProfileUpdateComponent,
    resolve: {
      studentProfile: StudentProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.studentProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
