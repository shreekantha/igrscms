import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentProfileService } from 'app/entities/student-profile/student-profile.service';
import { IStudentProfile, StudentProfile } from 'app/shared/model/student-profile.model';

describe('Service Tests', () => {
  describe('StudentProfile Service', () => {
    let injector: TestBed;
    let service: StudentProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudentProfile;
    let expectedResult: IStudentProfile | IStudentProfile[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StudentProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StudentProfile(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
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

      it('should create a StudentProfile', () => {
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

        service.create(new StudentProfile()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StudentProfile', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            img: 'BBBBBB',
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

      it('should return a list of StudentProfile', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            img: 'BBBBBB',
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

      it('should delete a StudentProfile', () => {
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
