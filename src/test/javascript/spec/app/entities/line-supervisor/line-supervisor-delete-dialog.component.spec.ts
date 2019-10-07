import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MobileCfTestModule } from '../../../test.module';
import { LineSupervisorDeleteDialogComponent } from 'app/entities/line-supervisor/line-supervisor-delete-dialog.component';
import { LineSupervisorService } from 'app/entities/line-supervisor/line-supervisor.service';

describe('Component Tests', () => {
  describe('LineSupervisor Management Delete Component', () => {
    let comp: LineSupervisorDeleteDialogComponent;
    let fixture: ComponentFixture<LineSupervisorDeleteDialogComponent>;
    let service: LineSupervisorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [LineSupervisorDeleteDialogComponent]
      })
        .overrideTemplate(LineSupervisorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineSupervisorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineSupervisorService);
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
