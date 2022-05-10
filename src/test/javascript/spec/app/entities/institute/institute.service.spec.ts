import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InstituteService } from 'app/entities/institute/institute.service';
import { IInstitute, Institute } from 'app/shared/model/institute.model';

describe('Service Tests', () => {
  describe('Institute Service', () => {
    let injector: TestBed;
    let service: InstituteService;
    let httpMock: HttpTestingController;
    let elemDefault: IInstitute;
    let expectedResult: IInstitute | IInstitute[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InstituteService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Institute(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Institute', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Institute()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Institute', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            shortName: 'BBBBBB',
            address: 'BBBBBB',
            email: 'BBBBBB',
            contact: 'BBBBBB',
            logoLink: 'BBBBBB',
            logo: 'BBBBBB',
            tagLine: 'BBBBBB',
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

      it('should return a list of Institute', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            shortName: 'BBBBBB',
            address: 'BBBBBB',
            email: 'BBBBBB',
            contact: 'BBBBBB',
            logoLink: 'BBBBBB',
            logo: 'BBBBBB',
            tagLine: 'BBBBBB',
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

      it('should delete a Institute', () => {
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
