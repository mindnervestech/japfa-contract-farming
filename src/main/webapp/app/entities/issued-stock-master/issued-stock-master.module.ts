import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { IssuedStockMasterComponent } from './issued-stock-master.component';
import { IssuedStockMasterDetailComponent } from './issued-stock-master-detail.component';
import { IssuedStockMasterUpdateComponent } from './issued-stock-master-update.component';
import {
  IssuedStockMasterDeletePopupComponent,
  IssuedStockMasterDeleteDialogComponent
} from './issued-stock-master-delete-dialog.component';
import { issuedStockMasterRoute, issuedStockMasterPopupRoute } from './issued-stock-master.route';

const ENTITY_STATES = [...issuedStockMasterRoute, ...issuedStockMasterPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IssuedStockMasterComponent,
    IssuedStockMasterDetailComponent,
    IssuedStockMasterUpdateComponent,
    IssuedStockMasterDeleteDialogComponent,
    IssuedStockMasterDeletePopupComponent
  ],
  entryComponents: [IssuedStockMasterDeleteDialogComponent]
})
export class MobileCfIssuedStockMasterModule {}
