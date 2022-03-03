import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { VisionUpdateComponent } from 'app/entities/vision/vision-update.component';
import { VisionService } from 'app/entities/vision/vision.service';
import { Vision } from 'app/shared/model/vision.model';

describe('Component Tests', () => {
  describe('Vision Management Update Component', () => {
    let comp: VisionUpdateComponent;
    let fixture: ComponentFixture<VisionUpdateComponent>;
    let service: VisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [VisionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VisionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VisionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VisionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vision(123);
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
        const entity = new Vision();
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
