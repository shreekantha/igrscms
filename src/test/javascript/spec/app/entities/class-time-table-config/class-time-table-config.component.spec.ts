import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { ClassTimeTableConfigComponent } from 'app/entities/class-time-table-config/class-time-table-config.component';
import { ClassTimeTableConfigService } from 'app/entities/class-time-table-config/class-time-table-config.service';
import { ClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';

describe('Component Tests', () => {
  describe('ClassTimeTableConfig Management Component', () => {
    let comp: ClassTimeTableConfigComponent;
    let fixture: ComponentFixture<ClassTimeTableConfigComponent>;
    let service: ClassTimeTableConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ClassTimeTableConfigComponent],
      })
        .overrideTemplate(ClassTimeTableConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassTimeTableConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassTimeTableConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClassTimeTableConfig(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.classTimeTableConfigs && comp.classTimeTableConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
