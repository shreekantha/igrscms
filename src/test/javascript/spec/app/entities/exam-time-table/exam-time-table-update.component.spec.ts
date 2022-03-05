import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ExamTimeTableUpdateComponent } from 'app/entities/exam-time-table/exam-time-table-update.component';
import { ExamTimeTableService } from 'app/entities/exam-time-table/exam-time-table.service';
import { ExamTimeTable } from 'app/shared/model/exam-time-table.model';

describe('Component Tests', () => {
  describe('ExamTimeTable Management Update Component', () => {
    let comp: ExamTimeTableUpdateComponent;
    let fixture: ComponentFixture<ExamTimeTableUpdateComponent>;
    let service: ExamTimeTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ExamTimeTableUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExamTimeTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamTimeTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamTimeTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExamTimeTable(123);
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
        const entity = new ExamTimeTable();
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
