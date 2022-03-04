import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { NoticeBoardDetailComponent } from 'app/entities/notice-board/notice-board-detail.component';
import { NoticeBoard } from 'app/shared/model/notice-board.model';

describe('Component Tests', () => {
  describe('NoticeBoard Management Detail Component', () => {
    let comp: NoticeBoardDetailComponent;
    let fixture: ComponentFixture<NoticeBoardDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ noticeBoard: new NoticeBoard(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [NoticeBoardDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NoticeBoardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NoticeBoardDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load noticeBoard on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.noticeBoard).toEqual(jasmine.objectContaining({ id: 123 }));
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
