import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVisionAndMission } from 'app/shared/model/vision-and-mission.model';

type EntityResponseType = HttpResponse<IVisionAndMission>;
type EntityArrayResponseType = HttpResponse<IVisionAndMission[]>;

@Injectable({ providedIn: 'root' })
export class VisionAndMissionService {
  public resourceUrl = SERVER_API_URL + 'api/vision-and-missions';

  constructor(protected http: HttpClient) {}

  create(visionAndMission: IVisionAndMission): Observable<EntityResponseType> {
    return this.http.post<IVisionAndMission>(this.resourceUrl, visionAndMission, { observe: 'response' });
  }

  update(visionAndMission: IVisionAndMission): Observable<EntityResponseType> {
    return this.http.put<IVisionAndMission>(this.resourceUrl, visionAndMission, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVisionAndMission>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVisionAndMission[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
