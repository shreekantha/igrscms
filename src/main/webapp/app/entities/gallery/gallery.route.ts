import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGallery, Gallery } from 'app/shared/model/gallery.model';
import { GalleryService } from './gallery.service';
import { GalleryComponent } from './gallery.component';
import { GalleryDetailComponent } from './gallery-detail.component';
import { GalleryUpdateComponent } from './gallery-update.component';

@Injectable({ providedIn: 'root' })
export class GalleryResolve implements Resolve<IGallery> {
  constructor(private service: GalleryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGallery> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gallery: HttpResponse<Gallery>) => {
          if (gallery.body) {
            return of(gallery.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Gallery());
  }
}

export const galleryRoute: Routes = [
  {
    path: '',
    component: GalleryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.gallery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GalleryDetailComponent,
    resolve: {
      gallery: GalleryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.gallery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GalleryUpdateComponent,
    resolve: {
      gallery: GalleryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.gallery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GalleryUpdateComponent,
    resolve: {
      gallery: GalleryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.gallery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
