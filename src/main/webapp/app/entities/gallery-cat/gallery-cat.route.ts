import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGalleryCat, GalleryCat } from 'app/shared/model/gallery-cat.model';
import { GalleryCatService } from './gallery-cat.service';
import { GalleryCatComponent } from './gallery-cat.component';
import { GalleryCatDetailComponent } from './gallery-cat-detail.component';
import { GalleryCatUpdateComponent } from './gallery-cat-update.component';

@Injectable({ providedIn: 'root' })
export class GalleryCatResolve implements Resolve<IGalleryCat> {
  constructor(private service: GalleryCatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGalleryCat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((galleryCat: HttpResponse<GalleryCat>) => {
          if (galleryCat.body) {
            return of(galleryCat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GalleryCat());
  }
}

export const galleryCatRoute: Routes = [
  {
    path: '',
    component: GalleryCatComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.galleryCat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GalleryCatDetailComponent,
    resolve: {
      galleryCat: GalleryCatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.galleryCat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GalleryCatUpdateComponent,
    resolve: {
      galleryCat: GalleryCatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.galleryCat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GalleryCatUpdateComponent,
    resolve: {
      galleryCat: GalleryCatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.galleryCat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
