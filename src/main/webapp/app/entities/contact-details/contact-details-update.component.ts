import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IContactDetails, ContactDetails } from 'app/shared/model/contact-details.model';
import { ContactDetailsService } from './contact-details.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-contact-details-update',
  templateUrl: './contact-details-update.component.html',
})
export class ContactDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    address: [null, [Validators.required]],
    email: [],
    contact: [null, [Validators.required]],
    mapLink: [],
    tenantId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected contactDetailsService: ContactDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactDetails }) => {
      this.updateForm(contactDetails);
    });
  }

  updateForm(contactDetails: IContactDetails): void {
    this.editForm.patchValue({
      id: contactDetails.id,
      address: contactDetails.address,
      email: contactDetails.email,
      contact: contactDetails.contact,
      mapLink: contactDetails.mapLink,
      tenantId: contactDetails.tenantId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('igrscmsApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactDetails = this.createFromForm();
    if (contactDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.contactDetailsService.update(contactDetails));
    } else {
      this.subscribeToSaveResponse(this.contactDetailsService.create(contactDetails));
    }
  }

  private createFromForm(): IContactDetails {
    return {
      ...new ContactDetails(),
      id: this.editForm.get(['id'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      mapLink: this.editForm.get(['mapLink'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactDetails>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
