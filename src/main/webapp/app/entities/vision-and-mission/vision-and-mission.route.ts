import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVisionAndMission, VisionAndMission } from 'app/shared/model/vision-and-mission.model';
import { VisionAndMissionService } from './vision-and-mission.service';
import { VisionAndMissionComponent } from './vision-and-mission.component';
import { VisionAndMissionDetailComponent } from './vision-and-mission-detail.component';
import { VisionAndMissionUpdateComponent } from './vision-and-mission-update.component';

@Injectable({ providedIn: 'root' })
export class VisionAndMissionResolve implements Resolve<IVisionAndMission> {
  constructor(private service: VisionAndMissionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVisionAndMission> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((visionAndMission: HttpResponse<VisionAndMission>) => {
          if (visionAndMission.body) {
            return of(visionAndMission.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VisionAndMission());
  }
}

export const visionAndMissionRoute: Routes = [
  {
    path: '',
    component: VisionAndMissionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.visionAndMission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VisionAndMissionDetailComponent,
    resolve: {
      visionAndMission: VisionAndMissionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.visionAndMission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VisionAndMissionUpdateComponent,
    resolve: {
      visionAndMission: VisionAndMissionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.visionAndMission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VisionAndMissionUpdateComponent,
    resolve: {
      visionAndMission: VisionAndMissionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.visionAndMission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
