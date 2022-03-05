import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { ClassTimeTableComponent } from 'app/entities/class-time-table/class-time-table.component';
import { ClassTimeTableService } from 'app/entities/class-time-table/class-time-table.service';
import { ClassTimeTable } from 'app/shared/model/class-time-table.model';

describe('Component Tests', () => {
  describe('ClassTimeTable Management Component', () => {
    let comp: ClassTimeTableComponent;
    let fixture: ComponentFixture<ClassTimeTableComponent>;
    let service: ClassTimeTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ClassTimeTableComponent],
      })
        .overrideTemplate(ClassTimeTableComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassTimeTableComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassTimeTableService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClassTimeTable(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.classTimeTables && comp.classTimeTables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
