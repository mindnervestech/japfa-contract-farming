import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { CurrentStockMasterComponent } from './current-stock-master.component';
import { CurrentStockMasterDetailComponent } from './current-stock-master-detail.component';
import { CurrentStockMasterUpdateComponent } from './current-stock-master-update.component';
import {
  CurrentStockMasterDeletePopupComponent,
  CurrentStockMasterDeleteDialogComponent
} from './current-stock-master-delete-dialog.component';
import { currentStockMasterRoute, currentStockMasterPopupRoute } from './current-stock-master.route';

const ENTITY_STATES = [...currentStockMasterRoute, ...currentStockMasterPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CurrentStockMasterComponent,
    CurrentStockMasterDetailComponent,
    CurrentStockMasterUpdateComponent,
    CurrentStockMasterDeleteDialogComponent,
    CurrentStockMasterDeletePopupComponent
  ],
  entryComponents: [CurrentStockMasterDeleteDialogComponent]
})
export class MobileCfCurrentStockMasterModule {}
