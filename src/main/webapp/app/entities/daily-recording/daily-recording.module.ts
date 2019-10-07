import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MobileCfSharedModule } from 'app/shared/shared.module';
import { DailyRecordingComponent } from './daily-recording.component';
import { DailyRecordingDetailComponent } from './daily-recording-detail.component';
import { DailyRecordingUpdateComponent } from './daily-recording-update.component';
import { DailyRecordingDeletePopupComponent, DailyRecordingDeleteDialogComponent } from './daily-recording-delete-dialog.component';
import { dailyRecordingRoute, dailyRecordingPopupRoute } from './daily-recording.route';

const ENTITY_STATES = [...dailyRecordingRoute, ...dailyRecordingPopupRoute];

@NgModule({
  imports: [MobileCfSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DailyRecordingComponent,
    DailyRecordingDetailComponent,
    DailyRecordingUpdateComponent,
    DailyRecordingDeleteDialogComponent,
    DailyRecordingDeletePopupComponent
  ],
  entryComponents: [DailyRecordingDeleteDialogComponent]
})
export class MobileCfDailyRecordingModule {}
