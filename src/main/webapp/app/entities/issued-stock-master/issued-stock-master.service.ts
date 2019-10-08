import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIssuedStockMaster } from 'app/shared/model/issued-stock-master.model';

type EntityResponseType = HttpResponse<IIssuedStockMaster>;
type EntityArrayResponseType = HttpResponse<IIssuedStockMaster[]>;

@Injectable({ providedIn: 'root' })
export class IssuedStockMasterService {
  public resourceUrl = SERVER_API_URL + 'api/issued-stock-masters';

  constructor(protected http: HttpClient) {}

  create(issuedStockMaster: IIssuedStockMaster): Observable<EntityResponseType> {
    return this.http.post<IIssuedStockMaster>(this.resourceUrl, issuedStockMaster, { observe: 'response' });
  }

  update(issuedStockMaster: IIssuedStockMaster): Observable<EntityResponseType> {
    return this.http.put<IIssuedStockMaster>(this.resourceUrl, issuedStockMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssuedStockMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssuedStockMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
