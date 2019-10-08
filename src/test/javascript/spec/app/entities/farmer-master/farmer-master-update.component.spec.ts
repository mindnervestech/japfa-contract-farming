import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { FarmerMasterUpdateComponent } from 'app/entities/farmer-master/farmer-master-update.component';
import { FarmerMasterService } from 'app/entities/farmer-master/farmer-master.service';
import { FarmerMaster } from 'app/shared/model/farmer-master.model';

describe('Component Tests', () => {
  describe('FarmerMaster Management Update Component', () => {
    let comp: FarmerMasterUpdateComponent;
    let fixture: ComponentFixture<FarmerMasterUpdateComponent>;
    let service: FarmerMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [FarmerMasterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FarmerMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FarmerMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FarmerMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FarmerMaster(123);
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
        const entity = new FarmerMaster();
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
