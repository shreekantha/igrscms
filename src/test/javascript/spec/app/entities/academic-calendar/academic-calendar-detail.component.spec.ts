import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { AcademicCalendarDetailComponent } from 'app/entities/academic-calendar/academic-calendar-detail.component';
import { AcademicCalendar } from 'app/shared/model/academic-calendar.model';

describe('Component Tests', () => {
  describe('AcademicCalendar Management Detail Component', () => {
    let comp: AcademicCalendarDetailComponent;
    let fixture: ComponentFixture<AcademicCalendarDetailComponent>;
    const route = ({ data: of({ academicCalendar: new AcademicCalendar(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [AcademicCalendarDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AcademicCalendarDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AcademicCalendarDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load academicCalendar on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.academicCalendar).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
