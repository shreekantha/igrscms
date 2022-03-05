import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAcademicCalendar } from 'app/shared/model/academic-calendar.model';

type EntityResponseType = HttpResponse<IAcademicCalendar>;
type EntityArrayResponseType = HttpResponse<IAcademicCalendar[]>;

@Injectable({ providedIn: 'root' })
export class AcademicCalendarService {
  public resourceUrl = SERVER_API_URL + 'api/academic-calendars';

  constructor(protected http: HttpClient) {}

  create(academicCalendar: IAcademicCalendar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academicCalendar);
    return this.http
      .post<IAcademicCalendar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(academicCalendar: IAcademicCalendar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academicCalendar);
    return this.http
      .put<IAcademicCalendar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAcademicCalendar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAcademicCalendar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(academicCalendar: IAcademicCalendar): IAcademicCalendar {
    const copy: IAcademicCalendar = Object.assign({}, academicCalendar, {
      startDate:
        academicCalendar.startDate && academicCalendar.startDate.isValid() ? academicCalendar.startDate.format(DATE_FORMAT) : undefined,
      endDate: academicCalendar.endDate && academicCalendar.endDate.isValid() ? academicCalendar.endDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((academicCalendar: IAcademicCalendar) => {
        academicCalendar.startDate = academicCalendar.startDate ? moment(academicCalendar.startDate) : undefined;
        academicCalendar.endDate = academicCalendar.endDate ? moment(academicCalendar.endDate) : undefined;
      });
    }
    return res;
  }
}
