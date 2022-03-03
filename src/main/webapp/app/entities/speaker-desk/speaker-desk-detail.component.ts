import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISpeakerDesk } from 'app/shared/model/speaker-desk.model';

@Component({
  selector: 'jhi-speaker-desk-detail',
  templateUrl: './speaker-desk-detail.component.html',
})
export class SpeakerDeskDetailComponent implements OnInit {
  speakerDesk: ISpeakerDesk | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ speakerDesk }) => (this.speakerDesk = speakerDesk));
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
