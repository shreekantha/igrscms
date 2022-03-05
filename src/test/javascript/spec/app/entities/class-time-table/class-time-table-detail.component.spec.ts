import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ClassTimeTableDetailComponent } from 'app/entities/class-time-table/class-time-table-detail.component';
import { ClassTimeTable } from 'app/shared/model/class-time-table.model';

describe('Component Tests', () => {
  describe('ClassTimeTable Management Detail Component', () => {
    let comp: ClassTimeTableDetailComponent;
    let fixture: ComponentFixture<ClassTimeTableDetailComponent>;
    const route = ({ data: of({ classTimeTable: new ClassTimeTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ClassTimeTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ClassTimeTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassTimeTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classTimeTable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classTimeTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
