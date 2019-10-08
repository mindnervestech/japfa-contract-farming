import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IssuedStockMaster } from 'app/shared/model/issued-stock-master.model';
import { IssuedStockMasterService } from './issued-stock-master.service';
import { IssuedStockMasterComponent } from './issued-stock-master.component';
import { IssuedStockMasterDetailComponent } from './issued-stock-master-detail.component';
import { IssuedStockMasterUpdateComponent } from './issued-stock-master-update.component';
import { IssuedStockMasterDeletePopupComponent } from './issued-stock-master-delete-dialog.component';
import { IIssuedStockMaster } from 'app/shared/model/issued-stock-master.model';

@Injectable({ providedIn: 'root' })
export class IssuedStockMasterResolve implements Resolve<IIssuedStockMaster> {
  constructor(private service: IssuedStockMasterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIssuedStockMaster> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IssuedStockMaster>) => response.ok),
        map((issuedStockMaster: HttpResponse<IssuedStockMaster>) => issuedStockMaster.body)
      );
    }
    return of(new IssuedStockMaster());
  }
}

export const issuedStockMasterRoute: Routes = [
  {
    path: '',
    component: IssuedStockMasterComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.issuedStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IssuedStockMasterDetailComponent,
    resolve: {
      issuedStockMaster: IssuedStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.issuedStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IssuedStockMasterUpdateComponent,
    resolve: {
      issuedStockMaster: IssuedStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.issuedStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IssuedStockMasterUpdateComponent,
    resolve: {
      issuedStockMaster: IssuedStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.issuedStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const issuedStockMasterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IssuedStockMasterDeletePopupComponent,
    resolve: {
      issuedStockMaster: IssuedStockMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.issuedStockMaster.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
