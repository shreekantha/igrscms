import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGalleryCat } from 'app/shared/model/gallery-cat.model';

type EntityResponseType = HttpResponse<IGalleryCat>;
type EntityArrayResponseType = HttpResponse<IGalleryCat[]>;

@Injectable({ providedIn: 'root' })
export class GalleryCatService {
  public resourceUrl = SERVER_API_URL + 'api/gallery-cats';

  constructor(protected http: HttpClient) {}

  create(galleryCat: IGalleryCat): Observable<EntityResponseType> {
    return this.http.post<IGalleryCat>(this.resourceUrl, galleryCat, { observe: 'response' });
  }

  update(galleryCat: IGalleryCat): Observable<EntityResponseType> {
    return this.http.put<IGalleryCat>(this.resourceUrl, galleryCat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGalleryCat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGalleryCat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
