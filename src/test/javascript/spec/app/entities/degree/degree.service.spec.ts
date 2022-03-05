import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DegreeService } from 'app/entities/degree/degree.service';
import { IDegree, Degree } from 'app/shared/model/degree.model';
import { GraduationType } from 'app/shared/model/enumerations/graduation-type.model';
import { DegreeOrDeptType } from 'app/shared/model/enumerations/degree-or-dept-type.model';

describe('Service Tests', () => {
  describe('Degree Service', () => {
    let injector: TestBed;
    let service: DegreeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDegree;
    let expectedResult: IDegree | IDegree[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DegreeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Degree(0, 'AAAAAAA', 'AAAAAAA', GraduationType.SCHOOLING, DegreeOrDeptType.ACADEMIC, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Degree', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Degree()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Degree', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            alias: 'BBBBBB',
            graduationType: 'BBBBBB',
            type: 'BBBBBB',
            numOfYears: 1,
            multiBatch: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Degree', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            alias: 'BBBBBB',
            graduationType: 'BBBBBB',
            type: 'BBBBBB',
            numOfYears: 1,
            multiBatch: true,
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

      it('should delete a Degree', () => {
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
