import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDailyRecording } from 'app/shared/model/daily-recording.model';
import { AccountService } from 'app/core/auth/account.service';
import { DailyRecordingService } from './daily-recording.service';

@Component({
  selector: 'jhi-daily-recording',
  templateUrl: './daily-recording.component.html'
})
export class DailyRecordingComponent implements OnInit, OnDestroy {
  dailyRecordings: IDailyRecording[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dailyRecordingService: DailyRecordingService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dailyRecordingService
      .query()
      .pipe(
        filter((res: HttpResponse<IDailyRecording[]>) => res.ok),
        map((res: HttpResponse<IDailyRecording[]>) => res.body)
      )
      .subscribe(
        (res: IDailyRecording[]) => {
          this.dailyRecordings = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDailyRecordings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDailyRecording) {
    return item.id;
  }

  registerChangeInDailyRecordings() {
    this.eventSubscriber = this.eventManager.subscribe('dailyRecordingListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
