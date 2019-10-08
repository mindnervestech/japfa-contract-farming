import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurrentStockMaster } from 'app/shared/model/current-stock-master.model';

type EntityResponseType = HttpResponse<ICurrentStockMaster>;
type EntityArrayResponseType = HttpResponse<ICurrentStockMaster[]>;

@Injectable({ providedIn: 'root' })
export class CurrentStockMasterService {
  public resourceUrl = SERVER_API_URL + 'api/current-stock-masters';

  constructor(protected http: HttpClient) {}

  create(currentStockMaster: ICurrentStockMaster): Observable<EntityResponseType> {
    return this.http.post<ICurrentStockMaster>(this.resourceUrl, currentStockMaster, { observe: 'response' });
  }

  update(currentStockMaster: ICurrentStockMaster): Observable<EntityResponseType> {
    return this.http.put<ICurrentStockMaster>(this.resourceUrl, currentStockMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrentStockMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrentStockMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
