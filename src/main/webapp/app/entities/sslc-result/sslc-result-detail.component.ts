import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISslcResult } from 'app/shared/model/sslc-result.model';

@Component({
  selector: 'jhi-sslc-result-detail',
  templateUrl: './sslc-result-detail.component.html',
})
export class SslcResultDetailComponent implements OnInit {
  sslcResult: ISslcResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sslcResult }) => (this.sslcResult = sslcResult));
  }

  previousState(): void {
    window.history.back();
  }
}
