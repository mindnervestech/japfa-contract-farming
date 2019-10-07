import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDailyRecording } from 'app/shared/model/daily-recording.model';
import { DailyRecordingService } from './daily-recording.service';

@Component({
  selector: 'jhi-daily-recording-delete-dialog',
  templateUrl: './daily-recording-delete-dialog.component.html'
})
export class DailyRecordingDeleteDialogComponent {
  dailyRecording: IDailyRecording;

  constructor(
    protected dailyRecordingService: DailyRecordingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dailyRecordingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dailyRecordingListModification',
        content: 'Deleted an dailyRecording'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-daily-recording-delete-popup',
  template: ''
})
export class DailyRecordingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dailyRecording }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DailyRecordingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dailyRecording = dailyRecording;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/daily-recording', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/daily-recording', { outlets: { popup: null } }]);
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
