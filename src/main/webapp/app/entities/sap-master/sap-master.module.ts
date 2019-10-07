import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { SAPMasterComponent } from './sap-master.component';
import { SAPMasterDetailComponent } from './sap-master-detail.component';
import { SAPMasterUpdateComponent } from './sap-master-update.component';
import { SAPMasterDeletePopupComponent, SAPMasterDeleteDialogComponent } from './sap-master-delete-dialog.component';
import { sAPMasterRoute, sAPMasterPopupRoute } from './sap-master.route';

const ENTITY_STATES = [...sAPMasterRoute, ...sAPMasterPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SAPMasterComponent,
    SAPMasterDetailComponent,
    SAPMasterUpdateComponent,
    SAPMasterDeleteDialogComponent,
    SAPMasterDeletePopupComponent
  ],
  entryComponents: [SAPMasterDeleteDialogComponent]
})
export class MobileCfSAPMasterModule {}
