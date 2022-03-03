import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAboutUs } from 'app/shared/model/about-us.model';

type EntityResponseType = HttpResponse<IAboutUs>;
type EntityArrayResponseType = HttpResponse<IAboutUs[]>;

@Injectable({ providedIn: 'root' })
export class AboutUsService {
  public resourceUrl = SERVER_API_URL + 'api/aboutuses';

  constructor(protected http: HttpClient) {}

  create(aboutUs: IAboutUs): Observable<EntityResponseType> {
    return this.http.post<IAboutUs>(this.resourceUrl, aboutUs, { observe: 'response' });
  }

  update(aboutUs: IAboutUs): Observable<EntityResponseType> {
    return this.http.put<IAboutUs>(this.resourceUrl, aboutUs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAboutUs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAboutUs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
