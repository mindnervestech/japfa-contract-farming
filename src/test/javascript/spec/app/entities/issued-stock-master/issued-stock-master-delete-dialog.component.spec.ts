import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MobileCfTestModule } from '../../../test.module';
import { IssuedStockMasterDeleteDialogComponent } from 'app/entities/issued-stock-master/issued-stock-master-delete-dialog.component';
import { IssuedStockMasterService } from 'app/entities/issued-stock-master/issued-stock-master.service';

describe('Component Tests', () => {
  describe('IssuedStockMaster Management Delete Component', () => {
    let comp: IssuedStockMasterDeleteDialogComponent;
    let fixture: ComponentFixture<IssuedStockMasterDeleteDialogComponent>;
    let service: IssuedStockMasterService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [IssuedStockMasterDeleteDialogComponent]
      })
        .overrideTemplate(IssuedStockMasterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssuedStockMasterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssuedStockMasterService);
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
