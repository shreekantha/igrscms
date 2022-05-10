import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHomeImg, HomeImg } from 'app/shared/model/home-img.model';
import { HomeImgService } from './home-img.service';
import { HomeImgComponent } from './home-img.component';
import { HomeImgDetailComponent } from './home-img-detail.component';
import { HomeImgUpdateComponent } from './home-img-update.component';

@Injectable({ providedIn: 'root' })
export class HomeImgResolve implements Resolve<IHomeImg> {
  constructor(private service: HomeImgService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHomeImg> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((homeImg: HttpResponse<HomeImg>) => {
          if (homeImg.body) {
            return of(homeImg.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HomeImg());
  }
}

export const homeImgRoute: Routes = [
  {
    path: '',
    component: HomeImgComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.homeImg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HomeImgDetailComponent,
    resolve: {
      homeImg: HomeImgResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.homeImg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HomeImgUpdateComponent,
    resolve: {
      homeImg: HomeImgResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.homeImg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HomeImgUpdateComponent,
    resolve: {
      homeImg: HomeImgResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.homeImg.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
