import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { AboutUsUpdateComponent } from 'app/entities/about-us/about-us-update.component';
import { AboutUsService } from 'app/entities/about-us/about-us.service';
import { AboutUs } from 'app/shared/model/about-us.model';

describe('Component Tests', () => {
  describe('AboutUs Management Update Component', () => {
    let comp: AboutUsUpdateComponent;
    let fixture: ComponentFixture<AboutUsUpdateComponent>;
    let service: AboutUsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [AboutUsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AboutUsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AboutUsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AboutUsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AboutUs(123);
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
        const entity = new AboutUs();
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
