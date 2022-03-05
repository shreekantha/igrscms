import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { DegreeUpdateComponent } from 'app/entities/degree/degree-update.component';
import { DegreeService } from 'app/entities/degree/degree.service';
import { Degree } from 'app/shared/model/degree.model';

describe('Component Tests', () => {
  describe('Degree Management Update Component', () => {
    let comp: DegreeUpdateComponent;
    let fixture: ComponentFixture<DegreeUpdateComponent>;
    let service: DegreeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [DegreeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DegreeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DegreeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DegreeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Degree(123);
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
        const entity = new Degree();
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
