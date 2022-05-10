import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { AlumniProfileUpdateComponent } from 'app/entities/alumni-profile/alumni-profile-update.component';
import { AlumniProfileService } from 'app/entities/alumni-profile/alumni-profile.service';
import { AlumniProfile } from 'app/shared/model/alumni-profile.model';

describe('Component Tests', () => {
  describe('AlumniProfile Management Update Component', () => {
    let comp: AlumniProfileUpdateComponent;
    let fixture: ComponentFixture<AlumniProfileUpdateComponent>;
    let service: AlumniProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [AlumniProfileUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlumniProfileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlumniProfileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlumniProfileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlumniProfile(123);
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
        const entity = new AlumniProfile();
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
