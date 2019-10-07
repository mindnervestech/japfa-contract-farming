import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDailyRecording } from 'app/shared/model/daily-recording.model';

type EntityResponseType = HttpResponse<IDailyRecording>;
type EntityArrayResponseType = HttpResponse<IDailyRecording[]>;

@Injectable({ providedIn: 'root' })
export class DailyRecordingService {
  public resourceUrl = SERVER_API_URL + 'api/daily-recordings';

  constructor(protected http: HttpClient) {}

  create(dailyRecording: IDailyRecording): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyRecording);
    return this.http
      .post<IDailyRecording>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dailyRecording: IDailyRecording): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyRecording);
    return this.http
      .put<IDailyRecording>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDailyRecording>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyRecording[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dailyRecording: IDailyRecording): IDailyRecording {
    const copy: IDailyRecording = Object.assign({}, dailyRecording, {
      createdOn:
        dailyRecording.createdOn != null && dailyRecording.createdOn.isValid() ? dailyRecording.createdOn.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dailyRecording: IDailyRecording) => {
        dailyRecording.createdOn = dailyRecording.createdOn != null ? moment(dailyRecording.createdOn) : null;
      });
    }
    return res;
  }
}
