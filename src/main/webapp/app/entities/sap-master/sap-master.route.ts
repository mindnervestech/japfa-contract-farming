import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SAPMaster } from 'app/shared/model/sap-master.model';
import { SAPMasterService } from './sap-master.service';
import { SAPMasterComponent } from './sap-master.component';
import { SAPMasterDetailComponent } from './sap-master-detail.component';
import { SAPMasterUpdateComponent } from './sap-master-update.component';
import { SAPMasterDeletePopupComponent } from './sap-master-delete-dialog.component';
import { ISAPMaster } from 'app/shared/model/sap-master.model';

@Injectable({ providedIn: 'root' })
export class SAPMasterResolve implements Resolve<ISAPMaster> {
  constructor(private service: SAPMasterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISAPMaster> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SAPMaster>) => response.ok),
        map((sAPMaster: HttpResponse<SAPMaster>) => sAPMaster.body)
      );
    }
    return of(new SAPMaster());
  }
}

export const sAPMasterRoute: Routes = [
  {
    path: '',
    component: SAPMasterComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.sAPMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SAPMasterDetailComponent,
    resolve: {
      sAPMaster: SAPMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.sAPMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SAPMasterUpdateComponent,
    resolve: {
      sAPMaster: SAPMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.sAPMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SAPMasterUpdateComponent,
    resolve: {
      sAPMaster: SAPMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.sAPMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sAPMasterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SAPMasterDeletePopupComponent,
    resolve: {
      sAPMaster: SAPMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.sAPMaster.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
