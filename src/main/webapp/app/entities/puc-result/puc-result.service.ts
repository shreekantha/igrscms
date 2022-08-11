import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPucResult } from 'app/shared/model/puc-result.model';

type EntityResponseType = HttpResponse<IPucResult>;
type EntityArrayResponseType = HttpResponse<IPucResult[]>;

@Injectable({ providedIn: 'root' })
export class PucResultService {
  public resourceUrl = SERVER_API_URL + 'api/puc-results';

  constructor(protected http: HttpClient) {}

  create(pucResult: IPucResult): Observable<EntityResponseType> {
    return this.http.post<IPucResult>(this.resourceUrl, pucResult, { observe: 'response' });
  }

  update(pucResult: IPucResult): Observable<EntityResponseType> {
    return this.http.put<IPucResult>(this.resourceUrl, pucResult, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPucResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPucResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
