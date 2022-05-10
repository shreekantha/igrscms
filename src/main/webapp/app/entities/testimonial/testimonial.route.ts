import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITestimonial, Testimonial } from 'app/shared/model/testimonial.model';
import { TestimonialService } from './testimonial.service';
import { TestimonialComponent } from './testimonial.component';
import { TestimonialDetailComponent } from './testimonial-detail.component';
import { TestimonialUpdateComponent } from './testimonial-update.component';

@Injectable({ providedIn: 'root' })
export class TestimonialResolve implements Resolve<ITestimonial> {
  constructor(private service: TestimonialService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITestimonial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((testimonial: HttpResponse<Testimonial>) => {
          if (testimonial.body) {
            return of(testimonial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Testimonial());
  }
}

export const testimonialRoute: Routes = [
  {
    path: '',
    component: TestimonialComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'igrscmsApp.testimonial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TestimonialDetailComponent,
    resolve: {
      testimonial: TestimonialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.testimonial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TestimonialUpdateComponent,
    resolve: {
      testimonial: TestimonialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.testimonial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TestimonialUpdateComponent,
    resolve: {
      testimonial: TestimonialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'igrscmsApp.testimonial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
