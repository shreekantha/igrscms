import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ClassTimeTableConfigDetailComponent } from 'app/entities/class-time-table-config/class-time-table-config-detail.component';
import { ClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';

describe('Component Tests', () => {
  describe('ClassTimeTableConfig Management Detail Component', () => {
    let comp: ClassTimeTableConfigDetailComponent;
    let fixture: ComponentFixture<ClassTimeTableConfigDetailComponent>;
    const route = ({ data: of({ classTimeTableConfig: new ClassTimeTableConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ClassTimeTableConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ClassTimeTableConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassTimeTableConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classTimeTableConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classTimeTableConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
