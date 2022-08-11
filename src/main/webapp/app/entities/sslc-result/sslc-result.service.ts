import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISslcResult } from 'app/shared/model/sslc-result.model';

type EntityResponseType = HttpResponse<ISslcResult>;
type EntityArrayResponseType = HttpResponse<ISslcResult[]>;

@Injectable({ providedIn: 'root' })
export class SslcResultService {
  public resourceUrl = SERVER_API_URL + 'api/sslc-results';

  constructor(protected http: HttpClient) {}

  create(sslcResult: ISslcResult): Observable<EntityResponseType> {
    return this.http.post<ISslcResult>(this.resourceUrl, sslcResult, { observe: 'response' });
  }

  update(sslcResult: ISslcResult): Observable<EntityResponseType> {
    return this.http.put<ISslcResult>(this.resourceUrl, sslcResult, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISslcResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISslcResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
