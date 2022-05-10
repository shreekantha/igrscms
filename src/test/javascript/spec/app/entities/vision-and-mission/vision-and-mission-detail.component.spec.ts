import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { VisionAndMissionDetailComponent } from 'app/entities/vision-and-mission/vision-and-mission-detail.component';
import { VisionAndMission } from 'app/shared/model/vision-and-mission.model';

describe('Component Tests', () => {
  describe('VisionAndMission Management Detail Component', () => {
    let comp: VisionAndMissionDetailComponent;
    let fixture: ComponentFixture<VisionAndMissionDetailComponent>;
    const route = ({ data: of({ visionAndMission: new VisionAndMission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [VisionAndMissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VisionAndMissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VisionAndMissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load visionAndMission on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.visionAndMission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
