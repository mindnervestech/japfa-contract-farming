import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'purchase-order',
        loadChildren: () => import('./purchase-order/purchase-order.module').then(m => m.MobileCfPurchaseOrderModule)
      },
      {
        path: 'mrn',
        loadChildren: () => import('./mrn/mrn.module').then(m => m.MobileCfMrnModule)
      },
      {
        path: 'line-supervisor',
        loadChildren: () => import('./line-supervisor/line-supervisor.module').then(m => m.MobileCfLineSupervisorModule)
      },
      {
        path: 'sap-master',
        loadChildren: () => import('./sap-master/sap-master.module').then(m => m.MobileCfSAPMasterModule)
      },
      {
        path: 'daily-recording',
        loadChildren: () => import('./daily-recording/daily-recording.module').then(m => m.MobileCfDailyRecordingModule)
      },
      {
        path: 'farmer-master',
        loadChildren: () => import('./farmer-master/farmer-master.module').then(m => m.MobileCfFarmerMasterModule)
      },
      {
        path: 'current-stock-master',
        loadChildren: () => import('./current-stock-master/current-stock-master.module').then(m => m.MobileCfCurrentStockMasterModule)
      },
      {
        path: 'issued-stock-master',
        loadChildren: () => import('./issued-stock-master/issued-stock-master.module').then(m => m.MobileCfIssuedStockMasterModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MobileCfEntityModule {}
