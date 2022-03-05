import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { AcademicCalendarUpdateComponent } from 'app/entities/academic-calendar/academic-calendar-update.component';
import { AcademicCalendarService } from 'app/entities/academic-calendar/academic-calendar.service';
import { AcademicCalendar } from 'app/shared/model/academic-calendar.model';

describe('Component Tests', () => {
  describe('AcademicCalendar Management Update Component', () => {
    let comp: AcademicCalendarUpdateComponent;
    let fixture: ComponentFixture<AcademicCalendarUpdateComponent>;
    let service: AcademicCalendarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [AcademicCalendarUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AcademicCalendarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcademicCalendarUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcademicCalendarService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcademicCalendar(123);
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
        const entity = new AcademicCalendar();
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
