import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVision, Vision } from 'app/shared/model/vision.model';
import { VisionService } from './vision.service';
import { VisionComponent } from './vision.component';
import { VisionDetailComponent } from './vision-detail.component';
import { VisionUpdateComponent } from './vision-update.component';

@Injectable({ providedIn: 'root' })
export class VisionResolve implements Resolve<IVision> {
  constructor(private service: VisionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVision> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vision: HttpResponse<Vision>) => {
          if (vision.body) {
            return of(vision.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vision());
  }
}

export const visionRoute: Routes = [
  {
    path: '',
    component: VisionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.vision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VisionDetailComponent,
    resolve: {
      vision: VisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.vision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VisionUpdateComponent,
    resolve: {
      vision: VisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.vision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VisionUpdateComponent,
    resolve: {
      vision: VisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.vision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
