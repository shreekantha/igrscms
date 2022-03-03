import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { GalleryCatDetailComponent } from 'app/entities/gallery-cat/gallery-cat-detail.component';
import { GalleryCat } from 'app/shared/model/gallery-cat.model';

describe('Component Tests', () => {
  describe('GalleryCat Management Detail Component', () => {
    let comp: GalleryCatDetailComponent;
    let fixture: ComponentFixture<GalleryCatDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ galleryCat: new GalleryCat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [GalleryCatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GalleryCatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GalleryCatDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load galleryCat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.galleryCat).toEqual(jasmine.objectContaining({ id: 123 }));
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
