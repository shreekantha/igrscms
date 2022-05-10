import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVisionAndMission } from 'app/shared/model/vision-and-mission.model';

@Component({
  selector: 'jhi-vision-and-mission-detail',
  templateUrl: './vision-and-mission-detail.component.html',
})
export class VisionAndMissionDetailComponent implements OnInit {
  visionAndMission: IVisionAndMission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visionAndMission }) => (this.visionAndMission = visionAndMission));
  }

  previousState(): void {
    window.history.back();
  }
}
