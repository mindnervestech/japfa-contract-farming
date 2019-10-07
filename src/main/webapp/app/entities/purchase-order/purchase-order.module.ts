import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { PurchaseOrderComponent } from './purchase-order.component';
import { PurchaseOrderDetailComponent } from './purchase-order-detail.component';
import { PurchaseOrderUpdateComponent } from './purchase-order-update.component';
import { PurchaseOrderDeletePopupComponent, PurchaseOrderDeleteDialogComponent } from './purchase-order-delete-dialog.component';
import { purchaseOrderRoute, purchaseOrderPopupRoute } from './purchase-order.route';

const ENTITY_STATES = [...purchaseOrderRoute, ...purchaseOrderPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PurchaseOrderComponent,
    PurchaseOrderDetailComponent,
    PurchaseOrderUpdateComponent,
    PurchaseOrderDeleteDialogComponent,
    PurchaseOrderDeletePopupComponent
  ],
  entryComponents: [PurchaseOrderDeleteDialogComponent]
})
export class MobileCfPurchaseOrderModule {}
