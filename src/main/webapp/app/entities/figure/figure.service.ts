import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFigure } from 'app/shared/model/figure.model';

type EntityResponseType = HttpResponse<IFigure>;
type EntityArrayResponseType = HttpResponse<IFigure[]>;

@Injectable({ providedIn: 'root' })
export class FigureService {
  public resourceUrl = SERVER_API_URL + 'api/figures';

  constructor(protected http: HttpClient) {}

  create(figure: IFigure): Observable<EntityResponseType> {
    return this.http.post<IFigure>(this.resourceUrl, figure, { observe: 'response' });
  }

  update(figure: IFigure): Observable<EntityResponseType> {
    return this.http.put<IFigure>(this.resourceUrl, figure, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFigure>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFigure[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
