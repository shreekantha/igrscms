import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AlumniProfileService } from 'app/entities/alumni-profile/alumni-profile.service';
import { IAlumniProfile, AlumniProfile } from 'app/shared/model/alumni-profile.model';

describe('Service Tests', () => {
  describe('AlumniProfile Service', () => {
    let injector: TestBed;
    let service: AlumniProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlumniProfile;
    let expectedResult: IAlumniProfile | IAlumniProfile[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlumniProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AlumniProfile(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dob: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AlumniProfile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dob: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate,
          },
          returnedFromService
        );

        service.create(new AlumniProfile()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AlumniProfile', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            fathersName: 'BBBBBB',
            mothersName: 'BBBBBB',
            currentTerm: 1,
            joiningAcademicYear: 'BBBBBB',
            exitAcademicYear: 'BBBBBB',
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            aadhaar: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            tenantId: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AlumniProfile', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            fathersName: 'BBBBBB',
            mothersName: 'BBBBBB',
            currentTerm: 1,
            joiningAcademicYear: 'BBBBBB',
            exitAcademicYear: 'BBBBBB',
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            aadhaar: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            tenantId: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AlumniProfile', () => {
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
