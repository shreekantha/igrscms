import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IgrscmsTestModule } from '../../../test.module';
import { HomeImgComponent } from 'app/entities/home-img/home-img.component';
import { HomeImgService } from 'app/entities/home-img/home-img.service';
import { HomeImg } from 'app/shared/model/home-img.model';

describe('Component Tests', () => {
  describe('HomeImg Management Component', () => {
    let comp: HomeImgComponent;
    let fixture: ComponentFixture<HomeImgComponent>;
    let service: HomeImgService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [HomeImgComponent],
      })
        .overrideTemplate(HomeImgComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HomeImgComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HomeImgService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HomeImg(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.homeImgs && comp.homeImgs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
