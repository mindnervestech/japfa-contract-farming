import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFarmerMaster } from 'app/shared/model/farmer-master.model';
import { FarmerMasterService } from './farmer-master.service';

@Component({
  selector: 'jhi-farmer-master-delete-dialog',
  templateUrl: './farmer-master-delete-dialog.component.html'
})
export class FarmerMasterDeleteDialogComponent {
  farmerMaster: IFarmerMaster;

  constructor(
    protected farmerMasterService: FarmerMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.farmerMasterService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'farmerMasterListModification',
        content: 'Deleted an farmerMaster'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-farmer-master-delete-popup',
  template: ''
})
export class FarmerMasterDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ farmerMaster }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FarmerMasterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.farmerMaster = farmerMaster;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/farmer-master', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/farmer-master', { outlets: { popup: null } }]);
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
