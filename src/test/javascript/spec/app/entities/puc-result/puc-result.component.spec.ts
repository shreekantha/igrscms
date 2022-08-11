import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { PucResultComponent } from 'app/entities/puc-result/puc-result.component';
import { PucResultService } from 'app/entities/puc-result/puc-result.service';
import { PucResult } from 'app/shared/model/puc-result.model';

describe('Component Tests', () => {
  describe('PucResult Management Component', () => {
    let comp: PucResultComponent;
    let fixture: ComponentFixture<PucResultComponent>;
    let service: PucResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [PucResultComponent],
      })
        .overrideTemplate(PucResultComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PucResultComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PucResultService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PucResult(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pucResults && comp.pucResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
