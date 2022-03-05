import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ClassTimeTableUpdateComponent } from 'app/entities/class-time-table/class-time-table-update.component';
import { ClassTimeTableService } from 'app/entities/class-time-table/class-time-table.service';
import { ClassTimeTable } from 'app/shared/model/class-time-table.model';

describe('Component Tests', () => {
  describe('ClassTimeTable Management Update Component', () => {
    let comp: ClassTimeTableUpdateComponent;
    let fixture: ComponentFixture<ClassTimeTableUpdateComponent>;
    let service: ClassTimeTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ClassTimeTableUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ClassTimeTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassTimeTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassTimeTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClassTimeTable(123);
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
        const entity = new ClassTimeTable();
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
