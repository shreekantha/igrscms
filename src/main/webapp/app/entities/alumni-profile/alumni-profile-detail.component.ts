import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAlumniProfile } from 'app/shared/model/alumni-profile.model';

@Component({
  selector: 'jhi-alumni-profile-detail',
  templateUrl: './alumni-profile-detail.component.html',
})
export class AlumniProfileDetailComponent implements OnInit {
  alumniProfile: IAlumniProfile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alumniProfile }) => (this.alumniProfile = alumniProfile));
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
