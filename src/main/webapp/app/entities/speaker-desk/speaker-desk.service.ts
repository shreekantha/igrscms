import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpeakerDesk } from 'app/shared/model/speaker-desk.model';

type EntityResponseType = HttpResponse<ISpeakerDesk>;
type EntityArrayResponseType = HttpResponse<ISpeakerDesk[]>;

@Injectable({ providedIn: 'root' })
export class SpeakerDeskService {
  public resourceUrl = SERVER_API_URL + 'api/speaker-desks';

  constructor(protected http: HttpClient) {}

  create(speakerDesk: ISpeakerDesk): Observable<EntityResponseType> {
    return this.http.post<ISpeakerDesk>(this.resourceUrl, speakerDesk, { observe: 'response' });
  }

  update(speakerDesk: ISpeakerDesk): Observable<EntityResponseType> {
    return this.http.put<ISpeakerDesk>(this.resourceUrl, speakerDesk, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpeakerDesk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpeakerDesk[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
