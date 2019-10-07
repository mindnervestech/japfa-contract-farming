import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MobileCfTestModule } from '../../../test.module';
import { DailyRecordingDeleteDialogComponent } from 'app/entities/daily-recording/daily-recording-delete-dialog.component';
import { DailyRecordingService } from 'app/entities/daily-recording/daily-recording.service';

describe('Component Tests', () => {
  describe('DailyRecording Management Delete Component', () => {
    let comp: DailyRecordingDeleteDialogComponent;
    let fixture: ComponentFixture<DailyRecordingDeleteDialogComponent>;
    let service: DailyRecordingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [DailyRecordingDeleteDialogComponent]
      })
        .overrideTemplate(DailyRecordingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyRecordingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyRecordingService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
