import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AboutUsService } from 'app/entities/about-us/about-us.service';
import { IAboutUs, AboutUs } from 'app/shared/model/about-us.model';

describe('Service Tests', () => {
  describe('AboutUs Service', () => {
    let injector: TestBed;
    let service: AboutUsService;
    let httpMock: HttpTestingController;
    let elemDefault: IAboutUs;
    let expectedResult: IAboutUs | IAboutUs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AboutUsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AboutUs(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AboutUs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AboutUs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AboutUs', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AboutUs', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            imgLink: 'BBBBBB',
            img: 'BBBBBB',
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

      it('should delete a AboutUs', () => {
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
