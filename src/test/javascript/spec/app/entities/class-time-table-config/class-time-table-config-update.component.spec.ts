import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ClassTimeTableConfigUpdateComponent } from 'app/entities/class-time-table-config/class-time-table-config-update.component';
import { ClassTimeTableConfigService } from 'app/entities/class-time-table-config/class-time-table-config.service';
import { ClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';

describe('Component Tests', () => {
  describe('ClassTimeTableConfig Management Update Component', () => {
    let comp: ClassTimeTableConfigUpdateComponent;
    let fixture: ComponentFixture<ClassTimeTableConfigUpdateComponent>;
    let service: ClassTimeTableConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ClassTimeTableConfigUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ClassTimeTableConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassTimeTableConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassTimeTableConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClassTimeTableConfig(123);
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
        const entity = new ClassTimeTableConfig();
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
