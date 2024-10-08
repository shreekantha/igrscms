import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { PucResultUpdateComponent } from 'app/entities/puc-result/puc-result-update.component';
import { PucResultService } from 'app/entities/puc-result/puc-result.service';
import { PucResult } from 'app/shared/model/puc-result.model';

describe('Component Tests', () => {
  describe('PucResult Management Update Component', () => {
    let comp: PucResultUpdateComponent;
    let fixture: ComponentFixture<PucResultUpdateComponent>;
    let service: PucResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [PucResultUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PucResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PucResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PucResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PucResult(123);
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
        const entity = new PucResult();
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
