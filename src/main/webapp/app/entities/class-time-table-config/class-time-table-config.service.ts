import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClassTimeTableConfig } from 'app/shared/model/class-time-table-config.model';

type EntityResponseType = HttpResponse<IClassTimeTableConfig>;
type EntityArrayResponseType = HttpResponse<IClassTimeTableConfig[]>;

@Injectable({ providedIn: 'root' })
export class ClassTimeTableConfigService {
  public resourceUrl = SERVER_API_URL + 'api/class-time-table-configs';

  constructor(protected http: HttpClient) {}

  create(classTimeTableConfig: IClassTimeTableConfig): Observable<EntityResponseType> {
    return this.http.post<IClassTimeTableConfig>(this.resourceUrl, classTimeTableConfig, { observe: 'response' });
  }

  update(classTimeTableConfig: IClassTimeTableConfig): Observable<EntityResponseType> {
    return this.http.put<IClassTimeTableConfig>(this.resourceUrl, classTimeTableConfig, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassTimeTableConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassTimeTableConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
