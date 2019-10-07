import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILineSupervisor } from 'app/shared/model/line-supervisor.model';
import { AccountService } from 'app/core/auth/account.service';
import { LineSupervisorService } from './line-supervisor.service';

@Component({
  selector: 'jhi-line-supervisor',
  templateUrl: './line-supervisor.component.html'
})
export class LineSupervisorComponent implements OnInit, OnDestroy {
  lineSupervisors: ILineSupervisor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected lineSupervisorService: LineSupervisorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.lineSupervisorService
      .query()
      .pipe(
        filter((res: HttpResponse<ILineSupervisor[]>) => res.ok),
        map((res: HttpResponse<ILineSupervisor[]>) => res.body)
      )
      .subscribe(
        (res: ILineSupervisor[]) => {
          this.lineSupervisors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLineSupervisors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILineSupervisor) {
    return item.id;
  }

  registerChangeInLineSupervisors() {
    this.eventSubscriber = this.eventManager.subscribe('lineSupervisorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
