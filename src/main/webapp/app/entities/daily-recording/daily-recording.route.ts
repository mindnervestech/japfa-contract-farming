import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DailyRecording } from 'app/shared/model/daily-recording.model';
import { DailyRecordingService } from './daily-recording.service';
import { DailyRecordingComponent } from './daily-recording.component';
import { DailyRecordingDetailComponent } from './daily-recording-detail.component';
import { DailyRecordingUpdateComponent } from './daily-recording-update.component';
import { DailyRecordingDeletePopupComponent } from './daily-recording-delete-dialog.component';
import { IDailyRecording } from 'app/shared/model/daily-recording.model';

@Injectable({ providedIn: 'root' })
export class DailyRecordingResolve implements Resolve<IDailyRecording> {
  constructor(private service: DailyRecordingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDailyRecording> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DailyRecording>) => response.ok),
        map((dailyRecording: HttpResponse<DailyRecording>) => dailyRecording.body)
      );
    }
    return of(new DailyRecording());
  }
}

export const dailyRecordingRoute: Routes = [
  {
    path: '',
    component: DailyRecordingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.dailyRecording.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DailyRecordingDetailComponent,
    resolve: {
      dailyRecording: DailyRecordingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.dailyRecording.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DailyRecordingUpdateComponent,
    resolve: {
      dailyRecording: DailyRecordingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.dailyRecording.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DailyRecordingUpdateComponent,
    resolve: {
      dailyRecording: DailyRecordingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.dailyRecording.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dailyRecordingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DailyRecordingDeletePopupComponent,
    resolve: {
      dailyRecording: DailyRecordingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.dailyRecording.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
