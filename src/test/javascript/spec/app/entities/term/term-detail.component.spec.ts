import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { TermDetailComponent } from 'app/entities/term/term-detail.component';
import { Term } from 'app/shared/model/term.model';

describe('Component Tests', () => {
  describe('Term Management Detail Component', () => {
    let comp: TermDetailComponent;
    let fixture: ComponentFixture<TermDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ term: new Term(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [TermDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TermDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TermDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load term on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.term).toEqual(jasmine.objectContaining({ id: 123 }));
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
