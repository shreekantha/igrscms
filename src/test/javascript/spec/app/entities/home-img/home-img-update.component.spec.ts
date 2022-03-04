import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { HomeImgUpdateComponent } from 'app/entities/home-img/home-img-update.component';
import { HomeImgService } from 'app/entities/home-img/home-img.service';
import { HomeImg } from 'app/shared/model/home-img.model';

describe('Component Tests', () => {
  describe('HomeImg Management Update Component', () => {
    let comp: HomeImgUpdateComponent;
    let fixture: ComponentFixture<HomeImgUpdateComponent>;
    let service: HomeImgService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [HomeImgUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HomeImgUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HomeImgUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HomeImgService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HomeImg(123);
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
        const entity = new HomeImg();
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
