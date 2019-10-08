import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { FarmerMasterComponent } from './farmer-master.component';
import { FarmerMasterDetailComponent } from './farmer-master-detail.component';
import { FarmerMasterUpdateComponent } from './farmer-master-update.component';
import { FarmerMasterDeletePopupComponent, FarmerMasterDeleteDialogComponent } from './farmer-master-delete-dialog.component';
import { farmerMasterRoute, farmerMasterPopupRoute } from './farmer-master.route';

const ENTITY_STATES = [...farmerMasterRoute, ...farmerMasterPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FarmerMasterComponent,
    FarmerMasterDetailComponent,
    FarmerMasterUpdateComponent,
    FarmerMasterDeleteDialogComponent,
    FarmerMasterDeletePopupComponent
  ],
  entryComponents: [FarmerMasterDeleteDialogComponent]
})
export class MobileCfFarmerMasterModule {}
