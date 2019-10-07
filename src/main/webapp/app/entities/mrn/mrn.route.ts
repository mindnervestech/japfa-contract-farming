import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mrn } from 'app/shared/model/mrn.model';
import { MrnService } from './mrn.service';
import { MrnComponent } from './mrn.component';
import { MrnDetailComponent } from './mrn-detail.component';
import { MrnUpdateComponent } from './mrn-update.component';
import { MrnDeletePopupComponent } from './mrn-delete-dialog.component';
import { IMrn } from 'app/shared/model/mrn.model';

@Injectable({ providedIn: 'root' })
export class MrnResolve implements Resolve<IMrn> {
  constructor(private service: MrnService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMrn> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Mrn>) => response.ok),
        map((mrn: HttpResponse<Mrn>) => mrn.body)
      );
    }
    return of(new Mrn());
  }
}

export const mrnRoute: Routes = [
  {
    path: '',
    component: MrnComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.mrn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MrnDetailComponent,
    resolve: {
      mrn: MrnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.mrn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MrnUpdateComponent,
    resolve: {
      mrn: MrnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.mrn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MrnUpdateComponent,
    resolve: {
      mrn: MrnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.mrn.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mrnPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MrnDeletePopupComponent,
    resolve: {
      mrn: MrnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mobileCfApp.mrn.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
