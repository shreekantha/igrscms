import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { SpeakerDeskDetailComponent } from 'app/entities/speaker-desk/speaker-desk-detail.component';
import { SpeakerDesk } from 'app/shared/model/speaker-desk.model';

describe('Component Tests', () => {
  describe('SpeakerDesk Management Detail Component', () => {
    let comp: SpeakerDeskDetailComponent;
    let fixture: ComponentFixture<SpeakerDeskDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ speakerDesk: new SpeakerDesk(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [SpeakerDeskDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SpeakerDeskDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpeakerDeskDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load speakerDesk on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.speakerDesk).toEqual(jasmine.objectContaining({ id: 123 }));
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
