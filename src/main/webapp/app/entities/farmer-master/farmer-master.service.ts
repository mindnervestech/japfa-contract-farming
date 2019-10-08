import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFarmerMaster } from 'app/shared/model/farmer-master.model';

type EntityResponseType = HttpResponse<IFarmerMaster>;
type EntityArrayResponseType = HttpResponse<IFarmerMaster[]>;

@Injectable({ providedIn: 'root' })
export class FarmerMasterService {
  public resourceUrl = SERVER_API_URL + 'api/farmer-masters';

  constructor(protected http: HttpClient) {}

  create(farmerMaster: IFarmerMaster): Observable<EntityResponseType> {
    return this.http.post<IFarmerMaster>(this.resourceUrl, farmerMaster, { observe: 'response' });
  }

  update(farmerMaster: IFarmerMaster): Observable<EntityResponseType> {
    return this.http.put<IFarmerMaster>(this.resourceUrl, farmerMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFarmerMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFarmerMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
