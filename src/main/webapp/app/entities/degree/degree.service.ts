import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDegree } from 'app/shared/model/degree.model';

type EntityResponseType = HttpResponse<IDegree>;
type EntityArrayResponseType = HttpResponse<IDegree[]>;

@Injectable({ providedIn: 'root' })
export class DegreeService {
  public resourceUrl = SERVER_API_URL + 'api/degrees';

  constructor(protected http: HttpClient) {}

  create(degree: IDegree): Observable<EntityResponseType> {
    return this.http.post<IDegree>(this.resourceUrl, degree, { observe: 'response' });
  }

  update(degree: IDegree): Observable<EntityResponseType> {
    return this.http.put<IDegree>(this.resourceUrl, degree, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDegree>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDegree[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
