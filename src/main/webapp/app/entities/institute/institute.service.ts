import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInstitute } from 'app/shared/model/institute.model';

type EntityResponseType = HttpResponse<IInstitute>;
type EntityArrayResponseType = HttpResponse<IInstitute[]>;

@Injectable({ providedIn: 'root' })
export class InstituteService {
  public resourceUrl = SERVER_API_URL + 'api/institutes';

  constructor(protected http: HttpClient) {}

  create(institute: IInstitute): Observable<EntityResponseType> {
    return this.http.post<IInstitute>(this.resourceUrl, institute, { observe: 'response' });
  }

  update(institute: IInstitute): Observable<EntityResponseType> {
    return this.http.put<IInstitute>(this.resourceUrl, institute, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInstitute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInstitute[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
