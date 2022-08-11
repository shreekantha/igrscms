import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { PucResultDetailComponent } from 'app/entities/puc-result/puc-result-detail.component';
import { PucResult } from 'app/shared/model/puc-result.model';

describe('Component Tests', () => {
  describe('PucResult Management Detail Component', () => {
    let comp: PucResultDetailComponent;
    let fixture: ComponentFixture<PucResultDetailComponent>;
    const route = ({ data: of({ pucResult: new PucResult(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [PucResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PucResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PucResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pucResult on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pucResult).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
