import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INoticeBoard } from 'app/shared/model/notice-board.model';

type EntityResponseType = HttpResponse<INoticeBoard>;
type EntityArrayResponseType = HttpResponse<INoticeBoard[]>;

@Injectable({ providedIn: 'root' })
export class NoticeBoardService {
  public resourceUrl = SERVER_API_URL + 'api/notice-boards';

  constructor(protected http: HttpClient) {}

  create(noticeBoard: INoticeBoard): Observable<EntityResponseType> {
    return this.http.post<INoticeBoard>(this.resourceUrl, noticeBoard, { observe: 'response' });
  }

  update(noticeBoard: INoticeBoard): Observable<EntityResponseType> {
    return this.http.put<INoticeBoard>(this.resourceUrl, noticeBoard, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INoticeBoard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INoticeBoard[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
