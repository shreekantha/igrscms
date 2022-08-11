import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { SslcResultDetailComponent } from 'app/entities/sslc-result/sslc-result-detail.component';
import { SslcResult } from 'app/shared/model/sslc-result.model';

describe('Component Tests', () => {
  describe('SslcResult Management Detail Component', () => {
    let comp: SslcResultDetailComponent;
    let fixture: ComponentFixture<SslcResultDetailComponent>;
    const route = ({ data: of({ sslcResult: new SslcResult(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [SslcResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SslcResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SslcResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sslcResult on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sslcResult).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
