import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { GalleryCatUpdateComponent } from 'app/entities/gallery-cat/gallery-cat-update.component';
import { GalleryCatService } from 'app/entities/gallery-cat/gallery-cat.service';
import { GalleryCat } from 'app/shared/model/gallery-cat.model';

describe('Component Tests', () => {
  describe('GalleryCat Management Update Component', () => {
    let comp: GalleryCatUpdateComponent;
    let fixture: ComponentFixture<GalleryCatUpdateComponent>;
    let service: GalleryCatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [GalleryCatUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GalleryCatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GalleryCatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GalleryCatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GalleryCat(123);
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
        const entity = new GalleryCat();
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
