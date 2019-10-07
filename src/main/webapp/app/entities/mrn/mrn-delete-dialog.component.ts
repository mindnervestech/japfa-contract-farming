import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMrn } from 'app/shared/model/mrn.model';
import { MrnService } from './mrn.service';

@Component({
  selector: 'jhi-mrn-delete-dialog',
  templateUrl: './mrn-delete-dialog.component.html'
})
export class MrnDeleteDialogComponent {
  mrn: IMrn;

  constructor(protected mrnService: MrnService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.mrnService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'mrnListModification',
        content: 'Deleted an mrn'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mrn-delete-popup',
  template: ''
})
export class MrnDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mrn }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MrnDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.mrn = mrn;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/mrn', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/mrn', { outlets: { popup: null } }]);
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
