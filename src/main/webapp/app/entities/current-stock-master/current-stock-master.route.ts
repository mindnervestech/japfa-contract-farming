import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CurrentStockMaster } from 'app/shared/model/current-stock-master.model';
import { CurrentStockMasterService } from './current-stock-master.service';
import { CurrentStockMasterComponent } from './current-stock-master.component';
import { CurrentStockMasterDetailComponent } from './current-stock-master-detail.component';
import { CurrentStockMasterUpdateComponent } from './current-stock-master-update.component';
import { CurrentStockMasterDeletePopupComponent } from './current-stock-master-delete-dialog.component';
import { ICurrentStockMaster } from 'app/shared/model/current-stock-master.model';

@Injectable({ providedIn: 'root' })
export class CurrentStockMasterResolve implements Resolve<ICurrentStockMaster> {
  constructor(private service: CurrentStockMasterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICurrentStockMaster> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CurrentStockMaster>) => response.ok),
        map((currentStockMaster: HttpResponse<CurrentStockMaster>) => currentStockMaster.body)
      );
    }
    return of(new CurrentStockMaster());
  }
}

export const currentStockMasterRoute: Routes = [
  {
    path: '',
    component: CurrentStockMasterComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.currentStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CurrentStockMasterDetailComponent,
    resolve: {
      currentStockMaster: CurrentStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.currentStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CurrentStockMasterUpdateComponent,
    resolve: {
      currentStockMaster: CurrentStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.currentStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CurrentStockMasterUpdateComponent,
    resolve: {
      currentStockMaster: CurrentStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.currentStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const currentStockMasterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CurrentStockMasterDeletePopupComponent,
    resolve: {
      currentStockMaster: CurrentStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.currentStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
