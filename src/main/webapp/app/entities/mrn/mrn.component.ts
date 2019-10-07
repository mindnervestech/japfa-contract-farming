import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMrn } from 'app/shared/model/mrn.model';
import { AccountService } from 'app/core/auth/account.service';
import { MrnService } from './mrn.service';

@Component({
  selector: 'jhi-mrn',
  templateUrl: './mrn.component.html'
})
export class MrnComponent implements OnInit, OnDestroy {
  mrns: IMrn[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected mrnService: MrnService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.mrnService
      .query()
      .pipe(
        filter((res: HttpResponse<IMrn[]>) => res.ok),
        map((res: HttpResponse<IMrn[]>) => res.body)
      )
      .subscribe(
        (res: IMrn[]) => {
          this.mrns = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMrns();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMrn) {
    return item.id;
  }

  registerChangeInMrns() {
    this.eventSubscriber = this.eventManager.subscribe('mrnListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
