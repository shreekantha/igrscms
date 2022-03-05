import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { SchemeComponent } from 'app/entities/scheme/scheme.component';
import { SchemeService } from 'app/entities/scheme/scheme.service';
import { Scheme } from 'app/shared/model/scheme.model';

describe('Component Tests', () => {
  describe('Scheme Management Component', () => {
    let comp: SchemeComponent;
    let fixture: ComponentFixture<SchemeComponent>;
    let service: SchemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [SchemeComponent],
      })
        .overrideTemplate(SchemeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchemeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchemeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Scheme(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.schemes && comp.schemes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
