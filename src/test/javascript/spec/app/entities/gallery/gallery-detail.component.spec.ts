import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { GalleryDetailComponent } from 'app/entities/gallery/gallery-detail.component';
import { Gallery } from 'app/shared/model/gallery.model';

describe('Component Tests', () => {
  describe('Gallery Management Detail Component', () => {
    let comp: GalleryDetailComponent;
    let fixture: ComponentFixture<GalleryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ gallery: new Gallery(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [GalleryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GalleryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GalleryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load gallery on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gallery).toEqual(jasmine.objectContaining({ id: 123 }));
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
