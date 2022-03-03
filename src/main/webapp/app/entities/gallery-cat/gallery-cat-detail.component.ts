import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGalleryCat } from 'app/shared/model/gallery-cat.model';

@Component({
  selector: 'jhi-gallery-cat-detail',
  templateUrl: './gallery-cat-detail.component.html',
})
export class GalleryCatDetailComponent implements OnInit {
  galleryCat: IGalleryCat | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galleryCat }) => (this.galleryCat = galleryCat));
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
