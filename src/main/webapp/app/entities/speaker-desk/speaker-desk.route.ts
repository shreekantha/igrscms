import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpeakerDesk, SpeakerDesk } from 'app/shared/model/speaker-desk.model';
import { SpeakerDeskService } from './speaker-desk.service';
import { SpeakerDeskComponent } from './speaker-desk.component';
import { SpeakerDeskDetailComponent } from './speaker-desk-detail.component';
import { SpeakerDeskUpdateComponent } from './speaker-desk-update.component';

@Injectable({ providedIn: 'root' })
export class SpeakerDeskResolve implements Resolve<ISpeakerDesk> {
  constructor(private service: SpeakerDeskService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpeakerDesk> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((speakerDesk: HttpResponse<SpeakerDesk>) => {
          if (speakerDesk.body) {
            return of(speakerDesk.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SpeakerDesk());
  }
}

export const speakerDeskRoute: Routes = [
  {
    path: '',
    component: SpeakerDeskComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.speakerDesk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpeakerDeskDetailComponent,
    resolve: {
      speakerDesk: SpeakerDeskResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.speakerDesk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpeakerDeskUpdateComponent,
    resolve: {
      speakerDesk: SpeakerDeskResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.speakerDesk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpeakerDeskUpdateComponent,
    resolve: {
      speakerDesk: SpeakerDeskResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.speakerDesk.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
