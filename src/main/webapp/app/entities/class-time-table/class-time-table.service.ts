import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClassTimeTable } from 'app/shared/model/class-time-table.model';

type EntityResponseType = HttpResponse<IClassTimeTable>;
type EntityArrayResponseType = HttpResponse<IClassTimeTable[]>;

@Injectable({ providedIn: 'root' })
export class ClassTimeTableService {
  public resourceUrl = SERVER_API_URL + 'api/class-time-tables';

  constructor(protected http: HttpClient) {}

  create(classTimeTable: IClassTimeTable): Observable<EntityResponseType> {
    return this.http.post<IClassTimeTable>(this.resourceUrl, classTimeTable, { observe: 'response' });
  }

  update(classTimeTable: IClassTimeTable): Observable<EntityResponseType> {
    return this.http.put<IClassTimeTable>(this.resourceUrl, classTimeTable, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassTimeTable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassTimeTable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
