import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { PeriodComponent } from 'app/entities/period/period.component';
import { PeriodService } from 'app/entities/period/period.service';
import { Period } from 'app/shared/model/period.model';

describe('Component Tests', () => {
  describe('Period Management Component', () => {
    let comp: PeriodComponent;
    let fixture: ComponentFixture<PeriodComponent>;
    let service: PeriodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [PeriodComponent],
      })
        .overrideTemplate(PeriodComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Period(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.periods && comp.periods[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
