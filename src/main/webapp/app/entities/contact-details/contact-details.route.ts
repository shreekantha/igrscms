import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactDetails, ContactDetails } from 'app/shared/model/contact-details.model';
import { ContactDetailsService } from './contact-details.service';
import { ContactDetailsComponent } from './contact-details.component';
import { ContactDetailsDetailComponent } from './contact-details-detail.component';
import { ContactDetailsUpdateComponent } from './contact-details-update.component';

@Injectable({ providedIn: 'root' })
export class ContactDetailsResolve implements Resolve<IContactDetails> {
  constructor(private service: ContactDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactDetails: HttpResponse<ContactDetails>) => {
          if (contactDetails.body) {
            return of(contactDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactDetails());
  }
}

export const contactDetailsRoute: Routes = [
  {
    path: '',
    component: ContactDetailsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.contactDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactDetailsDetailComponent,
    resolve: {
      contactDetails: ContactDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.contactDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactDetailsUpdateComponent,
    resolve: {
      contactDetails: ContactDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.contactDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactDetailsUpdateComponent,
    resolve: {
      contactDetails: ContactDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.contactDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
