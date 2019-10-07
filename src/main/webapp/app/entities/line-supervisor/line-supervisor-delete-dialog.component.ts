import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILineSupervisor } from 'app/shared/model/line-supervisor.model';
import { LineSupervisorService } from './line-supervisor.service';

@Component({
  selector: 'jhi-line-supervisor-delete-dialog',
  templateUrl: './line-supervisor-delete-dialog.component.html'
})
export class LineSupervisorDeleteDialogComponent {
  lineSupervisor: ILineSupervisor;

  constructor(
    protected lineSupervisorService: LineSupervisorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lineSupervisorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'lineSupervisorListModification',
        content: 'Deleted an lineSupervisor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-line-supervisor-delete-popup',
  template: ''
})
export class LineSupervisorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lineSupervisor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LineSupervisorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.lineSupervisor = lineSupervisor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/line-supervisor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/line-supervisor', { outlets: { popup: null } }]);
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
