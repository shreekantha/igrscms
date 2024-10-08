import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITestimonial } from 'app/shared/model/testimonial.model';

@Component({
  selector: 'jhi-testimonial-detail',
  templateUrl: './testimonial-detail.component.html',
})
export class TestimonialDetailComponent implements OnInit {
  testimonial: ITestimonial | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testimonial }) => (this.testimonial = testimonial));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
