import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FarmerMaster } from 'app/shared/model/farmer-master.model';
import { FarmerMasterService } from './farmer-master.service';
import { FarmerMasterComponent } from './farmer-master.component';
import { FarmerMasterDetailComponent } from './farmer-master-detail.component';
import { FarmerMasterUpdateComponent } from './farmer-master-update.component';
import { FarmerMasterDeletePopupComponent } from './farmer-master-delete-dialog.component';
import { IFarmerMaster } from 'app/shared/model/farmer-master.model';

@Injectable({ providedIn: 'root' })
export class FarmerMasterResolve implements Resolve<IFarmerMaster> {
  constructor(private service: FarmerMasterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFarmerMaster> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FarmerMaster>) => response.ok),
        map((farmerMaster: HttpResponse<FarmerMaster>) => farmerMaster.body)
      );
    }
    return of(new FarmerMaster());
  }
}

export const farmerMasterRoute: Routes = [
  {
    path: '',
    component: FarmerMasterComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.farmerMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FarmerMasterDetailComponent,
    resolve: {
      farmerMaster: FarmerMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.farmerMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FarmerMasterUpdateComponent,
    resolve: {
      farmerMaster: FarmerMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.farmerMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FarmerMasterUpdateComponent,
    resolve: {
      farmerMaster: FarmerMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.farmerMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const farmerMasterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FarmerMasterDeletePopupComponent,
    resolve: {
      farmerMaster: FarmerMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.farmerMaster.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
