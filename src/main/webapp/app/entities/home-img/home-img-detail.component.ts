import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHomeImg } from 'app/shared/model/home-img.model';

@Component({
  selector: 'jhi-home-img-detail',
  templateUrl: './home-img-detail.component.html',
})
export class HomeImgDetailComponent implements OnInit {
  homeImg: IHomeImg | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ homeImg }) => (this.homeImg = homeImg));
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
