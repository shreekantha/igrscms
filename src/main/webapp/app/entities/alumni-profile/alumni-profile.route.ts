import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlumniProfile, AlumniProfile } from 'app/shared/model/alumni-profile.model';
import { AlumniProfileService } from './alumni-profile.service';
import { AlumniProfileComponent } from './alumni-profile.component';
import { AlumniProfileDetailComponent } from './alumni-profile-detail.component';
import { AlumniProfileUpdateComponent } from './alumni-profile-update.component';

@Injectable({ providedIn: 'root' })
export class AlumniProfileResolve implements Resolve<IAlumniProfile> {
  constructor(private service: AlumniProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlumniProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alumniProfile: HttpResponse<AlumniProfile>) => {
          if (alumniProfile.body) {
            return of(alumniProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlumniProfile());
  }
}

export const alumniProfileRoute: Routes = [
  {
    path: '',
    component: AlumniProfileComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.alumniProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlumniProfileDetailComponent,
    resolve: {
      alumniProfile: AlumniProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.alumniProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlumniProfileUpdateComponent,
    resolve: {
      alumniProfile: AlumniProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.alumniProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlumniProfileUpdateComponent,
    resolve: {
      alumniProfile: AlumniProfileResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.alumniProfile.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
