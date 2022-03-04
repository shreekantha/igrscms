import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHomeImg } from 'app/shared/model/home-img.model';

type EntityResponseType = HttpResponse<IHomeImg>;
type EntityArrayResponseType = HttpResponse<IHomeImg[]>;

@Injectable({ providedIn: 'root' })
export class HomeImgService {
  public resourceUrl = SERVER_API_URL + 'api/home-imgs';

  constructor(protected http: HttpClient) {}

  create(homeImg: IHomeImg): Observable<EntityResponseType> {
    return this.http.post<IHomeImg>(this.resourceUrl, homeImg, { observe: 'response' });
  }

  update(homeImg: IHomeImg): Observable<EntityResponseType> {
    return this.http.put<IHomeImg>(this.resourceUrl, homeImg, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHomeImg>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHomeImg[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
