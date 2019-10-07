import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMrn } from 'app/shared/model/mrn.model';

type EntityResponseType = HttpResponse<IMrn>;
type EntityArrayResponseType = HttpResponse<IMrn[]>;

@Injectable({ providedIn: 'root' })
export class MrnService {
  public resourceUrl = SERVER_API_URL + 'api/mrns';

  constructor(protected http: HttpClient) {}

  create(mrn: IMrn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mrn);
    return this.http
      .post<IMrn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mrn: IMrn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mrn);
    return this.http
      .put<IMrn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMrn>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMrn[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mrn: IMrn): IMrn {
    const copy: IMrn = Object.assign({}, mrn, {
      dCDate: mrn.dCDate != null && mrn.dCDate.isValid() ? mrn.dCDate.format(DATE_FORMAT) : null,
      postingDate: mrn.postingDate != null && mrn.postingDate.isValid() ? mrn.postingDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dCDate = res.body.dCDate != null ? moment(res.body.dCDate) : null;
      res.body.postingDate = res.body.postingDate != null ? moment(res.body.postingDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mrn: IMrn) => {
        mrn.dCDate = mrn.dCDate != null ? moment(mrn.dCDate) : null;
        mrn.postingDate = mrn.postingDate != null ? moment(mrn.postingDate) : null;
      });
    }
    return res;
  }
}
