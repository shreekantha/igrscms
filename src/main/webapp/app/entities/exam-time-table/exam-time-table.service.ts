import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamTimeTable } from 'app/shared/model/exam-time-table.model';

type EntityResponseType = HttpResponse<IExamTimeTable>;
type EntityArrayResponseType = HttpResponse<IExamTimeTable[]>;

@Injectable({ providedIn: 'root' })
export class ExamTimeTableService {
  public resourceUrl = SERVER_API_URL + 'api/exam-time-tables';

  constructor(protected http: HttpClient) {}

  create(examTimeTable: IExamTimeTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examTimeTable);
    return this.http
      .post<IExamTimeTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(examTimeTable: IExamTimeTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examTimeTable);
    return this.http
      .put<IExamTimeTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExamTimeTable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExamTimeTable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(examTimeTable: IExamTimeTable): IExamTimeTable {
    const copy: IExamTimeTable = Object.assign({}, examTimeTable, {
      date: examTimeTable.date && examTimeTable.date.isValid() ? examTimeTable.date.format(DATE_FORMAT) : undefined,
      startTime: examTimeTable.startTime && examTimeTable.startTime.isValid() ? examTimeTable.startTime.toJSON() : undefined,
      endTime: examTimeTable.endTime && examTimeTable.endTime.isValid() ? examTimeTable.endTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.startTime = res.body.startTime ? moment(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? moment(res.body.endTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((examTimeTable: IExamTimeTable) => {
        examTimeTable.date = examTimeTable.date ? moment(examTimeTable.date) : undefined;
        examTimeTable.startTime = examTimeTable.startTime ? moment(examTimeTable.startTime) : undefined;
        examTimeTable.endTime = examTimeTable.endTime ? moment(examTimeTable.endTime) : undefined;
      });
    }
    return res;
  }
}
