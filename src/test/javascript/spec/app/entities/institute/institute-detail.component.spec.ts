import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { InstituteDetailComponent } from 'app/entities/institute/institute-detail.component';
import { Institute } from 'app/shared/model/institute.model';

describe('Component Tests', () => {
  describe('Institute Management Detail Component', () => {
    let comp: InstituteDetailComponent;
    let fixture: ComponentFixture<InstituteDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ institute: new Institute(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [InstituteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InstituteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstituteDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load institute on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.institute).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
