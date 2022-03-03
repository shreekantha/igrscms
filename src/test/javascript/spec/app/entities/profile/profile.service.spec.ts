import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IProfile, Profile } from 'app/shared/model/profile.model';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';

describe('Service Tests', () => {
  describe('Profile Service', () => {
    let injector: TestBed;
    let service: ProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfile;
    let expectedResult: IProfile | IProfile[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Profile(
        0,
        Title.Dr,
        Designation.ASST_TEACHER_KANNADA,
        UserType.Teaching,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
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

      it('should create a Profile', () => {
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

        service.create(new Profile()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Profile', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            designation: 'BBBBBB',
            userType: 'BBBBBB',
            eduQual: 'BBBBBB',
            aboutMe: 'BBBBBB',
            hobbies: 'BBBBBB',
            aadhaar: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            facebookLink: 'BBBBBB',
            instaLink: 'BBBBBB',
            twitterLink: 'BBBBBB',
            linkedLink: 'BBBBBB',
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

      it('should return a list of Profile', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            designation: 'BBBBBB',
            userType: 'BBBBBB',
            eduQual: 'BBBBBB',
            aboutMe: 'BBBBBB',
            hobbies: 'BBBBBB',
            aadhaar: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            facebookLink: 'BBBBBB',
            instaLink: 'BBBBBB',
            twitterLink: 'BBBBBB',
            linkedLink: 'BBBBBB',
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

      it('should delete a Profile', () => {
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
