import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssuedStockMaster } from 'app/shared/model/issued-stock-master.model';
import { IssuedStockMasterService } from './issued-stock-master.service';

@Component({
  selector: 'jhi-issued-stock-master-delete-dialog',
  templateUrl: './issued-stock-master-delete-dialog.component.html'
})
export class IssuedStockMasterDeleteDialogComponent {
  issuedStockMaster: IIssuedStockMaster;

  constructor(
    protected issuedStockMasterService: IssuedStockMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.issuedStockMasterService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'issuedStockMasterListModification',
        content: 'Deleted an issuedStockMaster'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-issued-stock-master-delete-popup',
  template: ''
})
export class IssuedStockMasterDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ issuedStockMaster }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IssuedStockMasterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.issuedStockMaster = issuedStockMaster;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/issued-stock-master', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/issued-stock-master', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
