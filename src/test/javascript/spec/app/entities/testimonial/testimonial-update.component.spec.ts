import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { TestimonialUpdateComponent } from 'app/entities/testimonial/testimonial-update.component';
import { TestimonialService } from 'app/entities/testimonial/testimonial.service';
import { Testimonial } from 'app/shared/model/testimonial.model';

describe('Component Tests', () => {
  describe('Testimonial Management Update Component', () => {
    let comp: TestimonialUpdateComponent;
    let fixture: ComponentFixture<TestimonialUpdateComponent>;
    let service: TestimonialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [TestimonialUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TestimonialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestimonialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestimonialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Testimonial(123);
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
        const entity = new Testimonial();
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
