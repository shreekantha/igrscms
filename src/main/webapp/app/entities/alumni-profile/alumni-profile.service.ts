import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlumniProfile } from 'app/shared/model/alumni-profile.model';

type EntityResponseType = HttpResponse<IAlumniProfile>;
type EntityArrayResponseType = HttpResponse<IAlumniProfile[]>;

@Injectable({ providedIn: 'root' })
export class AlumniProfileService {
  public resourceUrl = SERVER_API_URL + 'api/alumni-profiles';

  constructor(protected http: HttpClient) {}

  create(alumniProfile: IAlumniProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alumniProfile);
    return this.http
      .post<IAlumniProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alumniProfile: IAlumniProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alumniProfile);
    return this.http
      .put<IAlumniProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlumniProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlumniProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alumniProfile: IAlumniProfile): IAlumniProfile {
    const copy: IAlumniProfile = Object.assign({}, alumniProfile, {
      dob: alumniProfile.dob && alumniProfile.dob.isValid() ? alumniProfile.dob.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((alumniProfile: IAlumniProfile) => {
        alumniProfile.dob = alumniProfile.dob ? moment(alumniProfile.dob) : undefined;
      });
    }
    return res;
  }
}
