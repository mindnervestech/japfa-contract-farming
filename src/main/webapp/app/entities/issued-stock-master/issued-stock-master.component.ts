import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIssuedStockMaster } from 'app/shared/model/issued-stock-master.model';
import { AccountService } from 'app/core/auth/account.service';
import { IssuedStockMasterService } from './issued-stock-master.service';

@Component({
  selector: 'jhi-issued-stock-master',
  templateUrl: './issued-stock-master.component.html'
})
export class IssuedStockMasterComponent implements OnInit, OnDestroy {
  issuedStockMasters: IIssuedStockMaster[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected issuedStockMasterService: IssuedStockMasterService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.issuedStockMasterService
      .query()
      .pipe(
        filter((res: HttpResponse<IIssuedStockMaster[]>) => res.ok),
        map((res: HttpResponse<IIssuedStockMaster[]>) => res.body)
      )
      .subscribe(
        (res: IIssuedStockMaster[]) => {
          this.issuedStockMasters = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInIssuedStockMasters();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIssuedStockMaster) {
    return item.id;
  }

  registerChangeInIssuedStockMasters() {
    this.eventSubscriber = this.eventManager.subscribe('issuedStockMasterListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
