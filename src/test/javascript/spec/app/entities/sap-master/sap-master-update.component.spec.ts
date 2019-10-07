import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { SAPMasterUpdateComponent } from 'app/entities/sap-master/sap-master-update.component';
import { SAPMasterService } from 'app/entities/sap-master/sap-master.service';
import { SAPMaster } from 'app/shared/model/sap-master.model';

describe('Component Tests', () => {
  describe('SAPMaster Management Update Component', () => {
    let comp: SAPMasterUpdateComponent;
    let fixture: ComponentFixture<SAPMasterUpdateComponent>;
    let service: SAPMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [SAPMasterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SAPMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SAPMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SAPMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SAPMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SAPMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
