import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { InstituteUpdateComponent } from 'app/entities/institute/institute-update.component';
import { InstituteService } from 'app/entities/institute/institute.service';
import { Institute } from 'app/shared/model/institute.model';

describe('Component Tests', () => {
  describe('Institute Management Update Component', () => {
    let comp: InstituteUpdateComponent;
    let fixture: ComponentFixture<InstituteUpdateComponent>;
    let service: InstituteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [InstituteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InstituteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstituteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstituteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Institute(123);
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
        const entity = new Institute();
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
