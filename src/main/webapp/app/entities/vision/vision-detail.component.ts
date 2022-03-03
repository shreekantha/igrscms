import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVision } from 'app/shared/model/vision.model';

@Component({
  selector: 'jhi-vision-detail',
  templateUrl: './vision-detail.component.html',
})
export class VisionDetailComponent implements OnInit {
  vision: IVision | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vision }) => (this.vision = vision));
  }

  previousState(): void {
    window.history.back();
  }
}
