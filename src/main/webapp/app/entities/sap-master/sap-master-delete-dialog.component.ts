import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISAPMaster } from 'app/shared/model/sap-master.model';
import { SAPMasterService } from './sap-master.service';

@Component({
  selector: 'jhi-sap-master-delete-dialog',
  templateUrl: './sap-master-delete-dialog.component.html'
})
export class SAPMasterDeleteDialogComponent {
  sAPMaster: ISAPMaster;

  constructor(protected sAPMasterService: SAPMasterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sAPMasterService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sAPMasterListModification',
        content: 'Deleted an sAPMaster'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sap-master-delete-popup',
  template: ''
})
export class SAPMasterDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sAPMaster }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SAPMasterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sAPMaster = sAPMaster;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sap-master', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sap-master', { outlets: { popup: null } }]);
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
