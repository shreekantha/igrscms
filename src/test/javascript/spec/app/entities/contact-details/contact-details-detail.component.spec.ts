import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { ContactDetailsDetailComponent } from 'app/entities/contact-details/contact-details-detail.component';
import { ContactDetails } from 'app/shared/model/contact-details.model';

describe('Component Tests', () => {
  describe('ContactDetails Management Detail Component', () => {
    let comp: ContactDetailsDetailComponent;
    let fixture: ComponentFixture<ContactDetailsDetailComponent>;
    const route = ({ data: of({ contactDetails: new ContactDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [ContactDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContactDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
