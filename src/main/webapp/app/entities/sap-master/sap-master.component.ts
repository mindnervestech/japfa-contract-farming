import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISAPMaster } from 'app/shared/model/sap-master.model';
import { AccountService } from 'app/core/auth/account.service';
import { SAPMasterService } from './sap-master.service';

@Component({
  selector: 'jhi-sap-master',
  templateUrl: './sap-master.component.html'
})
export class SAPMasterComponent implements OnInit, OnDestroy {
  sAPMasters: ISAPMaster[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sAPMasterService: SAPMasterService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sAPMasterService
      .query()
      .pipe(
        filter((res: HttpResponse<ISAPMaster[]>) => res.ok),
        map((res: HttpResponse<ISAPMaster[]>) => res.body)
      )
      .subscribe(
        (res: ISAPMaster[]) => {
          this.sAPMasters = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSAPMasters();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISAPMaster) {
    return item.id;
  }

  registerChangeInSAPMasters() {
    this.eventSubscriber = this.eventManager.subscribe('sAPMasterListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
