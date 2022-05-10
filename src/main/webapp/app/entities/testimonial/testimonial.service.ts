import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITestimonial } from 'app/shared/model/testimonial.model';

type EntityResponseType = HttpResponse<ITestimonial>;
type EntityArrayResponseType = HttpResponse<ITestimonial[]>;

@Injectable({ providedIn: 'root' })
export class TestimonialService {
  public resourceUrl = SERVER_API_URL + 'api/testimonials';

  constructor(protected http: HttpClient) {}

  create(testimonial: ITestimonial): Observable<EntityResponseType> {
    return this.http.post<ITestimonial>(this.resourceUrl, testimonial, { observe: 'response' });
  }

  update(testimonial: ITestimonial): Observable<EntityResponseType> {
    return this.http.put<ITestimonial>(this.resourceUrl, testimonial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITestimonial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITestimonial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
