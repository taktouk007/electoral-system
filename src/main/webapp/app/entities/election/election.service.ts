import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IElection } from 'app/shared/model/election.model';

type EntityResponseType = HttpResponse<IElection>;
type EntityArrayResponseType = HttpResponse<IElection[]>;

@Injectable({ providedIn: 'root' })
export class ElectionService {
  public resourceUrl = SERVER_API_URL + 'api/elections';

  constructor(protected http: HttpClient) {}

  create(election: IElection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(election);
    return this.http
      .post<IElection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(election: IElection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(election);
    return this.http
      .put<IElection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IElection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IElection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(election: IElection): IElection {
    const copy: IElection = Object.assign({}, election, {
      startDate: election.startDate && election.startDate.isValid() ? election.startDate.toJSON() : undefined,
      endDate: election.endDate && election.endDate.isValid() ? election.endDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((election: IElection) => {
        election.startDate = election.startDate ? moment(election.startDate) : undefined;
        election.endDate = election.endDate ? moment(election.endDate) : undefined;
      });
    }
    return res;
  }
}
