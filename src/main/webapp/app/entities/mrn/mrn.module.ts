import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { MrnComponent } from './mrn.component';
import { MrnDetailComponent } from './mrn-detail.component';
import { MrnUpdateComponent } from './mrn-update.component';
import { MrnDeletePopupComponent, MrnDeleteDialogComponent } from './mrn-delete-dialog.component';
import { mrnRoute, mrnPopupRoute } from './mrn.route';

const ENTITY_STATES = [...mrnRoute, ...mrnPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MrnComponent, MrnDetailComponent, MrnUpdateComponent, MrnDeleteDialogComponent, MrnDeletePopupComponent],
  entryComponents: [MrnDeleteDialogComponent]
})
export class MobileCfMrnModule {}
