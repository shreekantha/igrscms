import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { SpeakerDeskUpdateComponent } from 'app/entities/speaker-desk/speaker-desk-update.component';
import { SpeakerDeskService } from 'app/entities/speaker-desk/speaker-desk.service';
import { SpeakerDesk } from 'app/shared/model/speaker-desk.model';

describe('Component Tests', () => {
  describe('SpeakerDesk Management Update Component', () => {
    let comp: SpeakerDeskUpdateComponent;
    let fixture: ComponentFixture<SpeakerDeskUpdateComponent>;
    let service: SpeakerDeskService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [SpeakerDeskUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SpeakerDeskUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpeakerDeskUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpeakerDeskService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpeakerDesk(123);
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
        const entity = new SpeakerDesk();
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
