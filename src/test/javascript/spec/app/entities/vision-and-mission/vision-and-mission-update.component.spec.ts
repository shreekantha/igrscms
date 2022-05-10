import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { VisionAndMissionUpdateComponent } from 'app/entities/vision-and-mission/vision-and-mission-update.component';
import { VisionAndMissionService } from 'app/entities/vision-and-mission/vision-and-mission.service';
import { VisionAndMission } from 'app/shared/model/vision-and-mission.model';

describe('Component Tests', () => {
  describe('VisionAndMission Management Update Component', () => {
    let comp: VisionAndMissionUpdateComponent;
    let fixture: ComponentFixture<VisionAndMissionUpdateComponent>;
    let service: VisionAndMissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [VisionAndMissionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VisionAndMissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VisionAndMissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VisionAndMissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VisionAndMission(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new VisionAndMission();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
