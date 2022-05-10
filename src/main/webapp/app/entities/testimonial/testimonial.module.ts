import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { TestimonialComponent } from './testimonial.component';
import { TestimonialDetailComponent } from './testimonial-detail.component';
import { TestimonialUpdateComponent } from './testimonial-update.component';
import { TestimonialDeleteDialogComponent } from './testimonial-delete-dialog.component';
import { testimonialRoute } from './testimonial.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(testimonialRoute)],
  declarations: [TestimonialComponent, TestimonialDetailComponent, TestimonialUpdateComponent, TestimonialDeleteDialogComponent],
  entryComponents: [TestimonialDeleteDialogComponent],
})
export class IgrscmsTestimonialModule {}
