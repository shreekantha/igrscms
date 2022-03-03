import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ContactDetailsUpdateComponent } from 'app/entities/contact-details/contact-details-update.component';
import { ContactDetailsService } from 'app/entities/contact-details/contact-details.service';
import { ContactDetails } from 'app/shared/model/contact-details.model';

describe('Component Tests', () => {
  describe('ContactDetails Management Update Component', () => {
    let comp: ContactDetailsUpdateComponent;
    let fixture: ComponentFixture<ContactDetailsUpdateComponent>;
    let service: ContactDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ContactDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContactDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactDetails(123);
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
        const entity = new ContactDetails();
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
