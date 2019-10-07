import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LineSupervisor } from 'app/shared/model/line-supervisor.model';
import { LineSupervisorService } from './line-supervisor.service';
import { LineSupervisorComponent } from './line-supervisor.component';
import { LineSupervisorDetailComponent } from './line-supervisor-detail.component';
import { LineSupervisorUpdateComponent } from './line-supervisor-update.component';
import { LineSupervisorDeletePopupComponent } from './line-supervisor-delete-dialog.component';
import { ILineSupervisor } from 'app/shared/model/line-supervisor.model';

@Injectable({ providedIn: 'root' })
export class LineSupervisorResolve implements Resolve<ILineSupervisor> {
  constructor(private service: LineSupervisorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILineSupervisor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LineSupervisor>) => response.ok),
        map((lineSupervisor: HttpResponse<LineSupervisor>) => lineSupervisor.body)
      );
    }
    return of(new LineSupervisor());
  }
}

export const lineSupervisorRoute: Routes = [
  {
    path: '',
    component: LineSupervisorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.lineSupervisor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LineSupervisorDetailComponent,
    resolve: {
      lineSupervisor: LineSupervisorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.lineSupervisor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LineSupervisorUpdateComponent,
    resolve: {
      lineSupervisor: LineSupervisorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.lineSupervisor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LineSupervisorUpdateComponent,
    resolve: {
      lineSupervisor: LineSupervisorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.lineSupervisor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lineSupervisorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LineSupervisorDeletePopupComponent,
    resolve: {
      lineSupervisor: LineSupervisorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.lineSupervisor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
