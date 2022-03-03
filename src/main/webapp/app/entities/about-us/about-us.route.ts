import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAboutUs, AboutUs } from 'app/shared/model/about-us.model';
import { AboutUsService } from './about-us.service';
import { AboutUsComponent } from './about-us.component';
import { AboutUsDetailComponent } from './about-us-detail.component';
import { AboutUsUpdateComponent } from './about-us-update.component';

@Injectable({ providedIn: 'root' })
export class AboutUsResolve implements Resolve<IAboutUs> {
  constructor(private service: AboutUsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAboutUs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aboutUs: HttpResponse<AboutUs>) => {
          if (aboutUs.body) {
            return of(aboutUs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AboutUs());
  }
}

export const aboutUsRoute: Routes = [
  {
    path: '',
    component: AboutUsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.aboutUs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AboutUsDetailComponent,
    resolve: {
      aboutUs: AboutUsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.aboutUs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AboutUsUpdateComponent,
    resolve: {
      aboutUs: AboutUsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.aboutUs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AboutUsUpdateComponent,
    resolve: {
      aboutUs: AboutUsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.aboutUs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
