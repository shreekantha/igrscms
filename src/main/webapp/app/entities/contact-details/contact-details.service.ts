import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactDetails } from 'app/shared/model/contact-details.model';

type EntityResponseType = HttpResponse<IContactDetails>;
type EntityArrayResponseType = HttpResponse<IContactDetails[]>;

@Injectable({ providedIn: 'root' })
export class ContactDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/contact-details';

  constructor(protected http: HttpClient) {}

  create(contactDetails: IContactDetails): Observable<EntityResponseType> {
    return this.http.post<IContactDetails>(this.resourceUrl, contactDetails, { observe: 'response' });
  }

  update(contactDetails: IContactDetails): Observable<EntityResponseType> {
    return this.http.put<IContactDetails>(this.resourceUrl, contactDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
