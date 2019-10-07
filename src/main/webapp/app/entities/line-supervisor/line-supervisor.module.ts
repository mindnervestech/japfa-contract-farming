import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { LineSupervisorComponent } from './line-supervisor.component';
import { LineSupervisorDetailComponent } from './line-supervisor-detail.component';
import { LineSupervisorUpdateComponent } from './line-supervisor-update.component';
import { LineSupervisorDeletePopupComponent, LineSupervisorDeleteDialogComponent } from './line-supervisor-delete-dialog.component';
import { lineSupervisorRoute, lineSupervisorPopupRoute } from './line-supervisor.route';

const ENTITY_STATES = [...lineSupervisorRoute, ...lineSupervisorPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LineSupervisorComponent,
    LineSupervisorDetailComponent,
    LineSupervisorUpdateComponent,
    LineSupervisorDeleteDialogComponent,
    LineSupervisorDeletePopupComponent
  ],
  entryComponents: [LineSupervisorDeleteDialogComponent]
})
export class MobileCfLineSupervisorModule {}
