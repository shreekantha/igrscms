import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGallery } from 'app/shared/model/gallery.model';

type EntityResponseType = HttpResponse<IGallery>;
type EntityArrayResponseType = HttpResponse<IGallery[]>;

@Injectable({ providedIn: 'root' })
export class GalleryService {
  public resourceUrl = SERVER_API_URL + 'api/galleries';

  constructor(protected http: HttpClient) {}

  create(gallery: IGallery): Observable<EntityResponseType> {
    return this.http.post<IGallery>(this.resourceUrl, gallery, { observe: 'response' });
  }

  update(gallery: IGallery): Observable<EntityResponseType> {
    return this.http.put<IGallery>(this.resourceUrl, gallery, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGallery>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGallery[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
