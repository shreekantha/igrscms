import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { SslcResultComponent } from 'app/entities/sslc-result/sslc-result.component';
import { SslcResultService } from 'app/entities/sslc-result/sslc-result.service';
import { SslcResult } from 'app/shared/model/sslc-result.model';

describe('Component Tests', () => {
  describe('SslcResult Management Component', () => {
    let comp: SslcResultComponent;
    let fixture: ComponentFixture<SslcResultComponent>;
    let service: SslcResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [SslcResultComponent],
      })
        .overrideTemplate(SslcResultComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SslcResultComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SslcResultService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SslcResult(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sslcResults && comp.sslcResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
