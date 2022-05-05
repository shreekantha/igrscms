import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ClassTimeTableConfigService } from 'app/entities/class-time-table-config/class-time-table-config.service';
import { IClassTimeTableConfig, ClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';
import { TimeTableGenType } from 'app/shared/model/enumerations/time-table-gen-type.model';

describe('Service Tests', () => {
  describe('ClassTimeTableConfig Service', () => {
    let injector: TestBed;
    let service: ClassTimeTableConfigService;
    let httpMock: HttpTestingController;
    let elemDefault: IClassTimeTableConfig;
    let expectedResult: IClassTimeTableConfig | IClassTimeTableConfig[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ClassTimeTableConfigService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ClassTimeTableConfig(0, TimeTableGenType.ALL_DAYS_EXCEPT_SAT, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ClassTimeTableConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ClassTimeTableConfig()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ClassTimeTableConfig', () => {
        const returnedFromService = Object.assign(
          {
            timeTableGenType: 'BBBBBB',
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

      it('should return a list of ClassTimeTableConfig', () => {
        const returnedFromService = Object.assign(
          {
            timeTableGenType: 'BBBBBB',
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

      it('should delete a ClassTimeTableConfig', () => {
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
