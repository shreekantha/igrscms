import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVision } from 'app/shared/model/vision.model';

type EntityResponseType = HttpResponse<IVision>;
type EntityArrayResponseType = HttpResponse<IVision[]>;

@Injectable({ providedIn: 'root' })
export class VisionService {
  public resourceUrl = SERVER_API_URL + 'api/visions';

  constructor(protected http: HttpClient) {}

  create(vision: IVision): Observable<EntityResponseType> {
    return this.http.post<IVision>(this.resourceUrl, vision, { observe: 'response' });
  }

  update(vision: IVision): Observable<EntityResponseType> {
    return this.http.put<IVision>(this.resourceUrl, vision, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVision>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVision[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
