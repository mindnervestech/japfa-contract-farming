import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MobileCfTestModule } from '../../../test.module';
import { FarmerMasterDeleteDialogComponent } from 'app/entities/farmer-master/farmer-master-delete-dialog.component';
import { FarmerMasterService } from 'app/entities/farmer-master/farmer-master.service';

describe('Component Tests', () => {
  describe('FarmerMaster Management Delete Component', () => {
    let comp: FarmerMasterDeleteDialogComponent;
    let fixture: ComponentFixture<FarmerMasterDeleteDialogComponent>;
    let service: FarmerMasterService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [FarmerMasterDeleteDialogComponent]
      })
        .overrideTemplate(FarmerMasterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FarmerMasterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FarmerMasterService);
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
