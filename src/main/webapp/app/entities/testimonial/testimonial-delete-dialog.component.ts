import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestimonial } from 'app/shared/model/testimonial.model';
import { TestimonialService } from './testimonial.service';

@Component({
  templateUrl: './testimonial-delete-dialog.component.html',
})
export class TestimonialDeleteDialogComponent {
  testimonial?: ITestimonial;

  constructor(
    protected testimonialService: TestimonialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.testimonialService.delete(id).subscribe(() => {
      this.eventManager.broadcast('testimonialListModification');
      this.activeModal.close();
    });
  }
}
