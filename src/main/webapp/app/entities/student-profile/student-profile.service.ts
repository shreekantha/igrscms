import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStudentProfile } from 'app/shared/model/student-profile.model';

type EntityResponseType = HttpResponse<IStudentProfile>;
type EntityArrayResponseType = HttpResponse<IStudentProfile[]>;

@Injectable({ providedIn: 'root' })
export class StudentProfileService {
  public resourceUrl = SERVER_API_URL + 'api/student-profiles';

  constructor(protected http: HttpClient) {}

  create(studentProfile: IStudentProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentProfile);
    return this.http
      .post<IStudentProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studentProfile: IStudentProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentProfile);
    return this.http
      .put<IStudentProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudentProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(studentProfile: IStudentProfile): IStudentProfile {
    const copy: IStudentProfile = Object.assign({}, studentProfile, {
      dob: studentProfile.dob && studentProfile.dob.isValid() ? studentProfile.dob.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dob = res.body.dob ? moment(res.body.dob) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studentProfile: IStudentProfile) => {
        studentProfile.dob = studentProfile.dob ? moment(studentProfile.dob) : undefined;
      });
    }
    return res;
  }
}
