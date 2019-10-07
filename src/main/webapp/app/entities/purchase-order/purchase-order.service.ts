import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

type EntityResponseType = HttpResponse<IPurchaseOrder>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrder[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderService {
  public resourceUrl = SERVER_API_URL + 'api/purchase-orders';

  constructor(protected http: HttpClient) {}

  create(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
    return this.http.post<IPurchaseOrder>(this.resourceUrl, purchaseOrder, { observe: 'response' });
  }

  update(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
    return this.http.put<IPurchaseOrder>(this.resourceUrl, purchaseOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPurchaseOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPurchaseOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
