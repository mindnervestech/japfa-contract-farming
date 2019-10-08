import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICurrentStockMaster } from 'app/shared/model/current-stock-master.model';
import { AccountService } from 'app/core/auth/account.service';
import { CurrentStockMasterService } from './current-stock-master.service';

@Component({
  selector: 'jhi-current-stock-master',
  templateUrl: './current-stock-master.component.html'
})
export class CurrentStockMasterComponent implements OnInit, OnDestroy {
  currentStockMasters: ICurrentStockMaster[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected currentStockMasterService: CurrentStockMasterService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.currentStockMasterService
      .query()
      .pipe(
        filter((res: HttpResponse<ICurrentStockMaster[]>) => res.ok),
        map((res: HttpResponse<ICurrentStockMaster[]>) => res.body)
      )
      .subscribe(
        (res: ICurrentStockMaster[]) => {
          this.currentStockMasters = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCurrentStockMasters();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurrentStockMaster) {
    return item.id;
  }

  registerChangeInCurrentStockMasters() {
    this.eventSubscriber = this.eventManager.subscribe('currentStockMasterListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
