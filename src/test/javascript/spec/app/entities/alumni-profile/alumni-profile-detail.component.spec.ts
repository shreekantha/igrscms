import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IgrscmsTestModule } from '../../../test.module';
import { AlumniProfileDetailComponent } from 'app/entities/alumni-profile/alumni-profile-detail.component';
import { AlumniProfile } from 'app/shared/model/alumni-profile.model';

describe('Component Tests', () => {
  describe('AlumniProfile Management Detail Component', () => {
    let comp: AlumniProfileDetailComponent;
    let fixture: ComponentFixture<AlumniProfileDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ alumniProfile: new AlumniProfile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [AlumniProfileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlumniProfileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlumniProfileDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load alumniProfile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alumniProfile).toEqual(jasmine.objectContaining({ id: 123 }));
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
