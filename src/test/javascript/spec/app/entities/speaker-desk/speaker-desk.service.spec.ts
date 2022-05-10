import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SpeakerDeskService } from 'app/entities/speaker-desk/speaker-desk.service';
import { ISpeakerDesk, SpeakerDesk } from 'app/shared/model/speaker-desk.model';

describe('Service Tests', () => {
  describe('SpeakerDesk Service', () => {
    let injector: TestBed;
    let service: SpeakerDeskService;
    let httpMock: HttpTestingController;
    let elemDefault: ISpeakerDesk;
    let expectedResult: ISpeakerDesk | ISpeakerDesk[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SpeakerDeskService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SpeakerDesk(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SpeakerDesk', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SpeakerDesk()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SpeakerDesk', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            note: 'BBBBBB',
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            tenantId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SpeakerDesk', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            note: 'BBBBBB',
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            tenantId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SpeakerDesk', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
