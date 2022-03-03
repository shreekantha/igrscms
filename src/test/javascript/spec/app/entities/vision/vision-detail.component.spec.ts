import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { VisionDetailComponent } from 'app/entities/vision/vision-detail.component';
import { Vision } from 'app/shared/model/vision.model';

describe('Component Tests', () => {
  describe('Vision Management Detail Component', () => {
    let comp: VisionDetailComponent;
    let fixture: ComponentFixture<VisionDetailComponent>;
    const route = ({ data: of({ vision: new Vision(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [VisionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VisionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VisionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vision on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vision).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
