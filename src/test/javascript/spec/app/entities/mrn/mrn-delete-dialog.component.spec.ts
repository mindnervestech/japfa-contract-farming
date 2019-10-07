import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MobileCfTestModule } from '../../../test.module';
import { MrnDeleteDialogComponent } from 'app/entities/mrn/mrn-delete-dialog.component';
import { MrnService } from 'app/entities/mrn/mrn.service';

describe('Component Tests', () => {
  describe('Mrn Management Delete Component', () => {
    let comp: MrnDeleteDialogComponent;
    let fixture: ComponentFixture<MrnDeleteDialogComponent>;
    let service: MrnService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [MrnDeleteDialogComponent]
      })
        .overrideTemplate(MrnDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MrnDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MrnService);
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
