import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { SslcResultUpdateComponent } from 'app/entities/sslc-result/sslc-result-update.component';
import { SslcResultService } from 'app/entities/sslc-result/sslc-result.service';
import { SslcResult } from 'app/shared/model/sslc-result.model';

describe('Component Tests', () => {
  describe('SslcResult Management Update Component', () => {
    let comp: SslcResultUpdateComponent;
    let fixture: ComponentFixture<SslcResultUpdateComponent>;
    let service: SslcResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [SslcResultUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SslcResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SslcResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SslcResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SslcResult(123);
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
        const entity = new SslcResult();
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
