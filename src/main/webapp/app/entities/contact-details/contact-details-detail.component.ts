import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IContactDetails } from 'app/shared/model/contact-details.model';

@Component({
  selector: 'jhi-contact-details-detail',
  templateUrl: './contact-details-detail.component.html',
})
export class ContactDetailsDetailComponent implements OnInit {
  contactDetails: IContactDetails | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactDetails }) => (this.contactDetails = contactDetails));
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
