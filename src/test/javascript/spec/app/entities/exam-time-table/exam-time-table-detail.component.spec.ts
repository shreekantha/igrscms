import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ExamTimeTableDetailComponent } from 'app/entities/exam-time-table/exam-time-table-detail.component';
import { ExamTimeTable } from 'app/shared/model/exam-time-table.model';

describe('Component Tests', () => {
  describe('ExamTimeTable Management Detail Component', () => {
    let comp: ExamTimeTableDetailComponent;
    let fixture: ComponentFixture<ExamTimeTableDetailComponent>;
    const route = ({ data: of({ examTimeTable: new ExamTimeTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ExamTimeTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExamTimeTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExamTimeTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load examTimeTable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.examTimeTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
