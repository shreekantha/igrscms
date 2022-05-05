import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';

describe('Service Tests', () => {
  describe('UserProfile Service', () => {
    let injector: TestBed;
    let service: UserProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserProfile;
    let expectedResult: IUserProfile | IUserProfile[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserProfile(
        0,
        Title.Dr,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Designation.PRINCIPAL,
        UserType.Principal,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
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

      it('should create a UserProfile', () => {
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

        service.create(new UserProfile()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserProfile', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            designation: 'BBBBBB',
            userType: 'BBBBBB',
            eduQual: 'BBBBBB',
            aboutMe: 'BBBBBB',
            hobbies: 'BBBBBB',
            aadhaar: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            facebookLink: 'BBBBBB',
            instaLink: 'BBBBBB',
            twitterLink: 'BBBBBB',
            linkedLink: 'BBBBBB',
            tenantId: 'BBBBBB',
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

      it('should return a list of UserProfile', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            mobile: 'BBBBBB',
            email: 'BBBBBB',
            designation: 'BBBBBB',
            userType: 'BBBBBB',
            eduQual: 'BBBBBB',
            aboutMe: 'BBBBBB',
            hobbies: 'BBBBBB',
            aadhaar: 'BBBBBB',
            dob: currentDate.format(DATE_FORMAT),
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
            facebookLink: 'BBBBBB',
            instaLink: 'BBBBBB',
            twitterLink: 'BBBBBB',
            linkedLink: 'BBBBBB',
            tenantId: 'BBBBBB',
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

      it('should delete a UserProfile', () => {
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
