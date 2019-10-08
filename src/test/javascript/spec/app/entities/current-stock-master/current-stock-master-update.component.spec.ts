import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { CurrentStockMasterUpdateComponent } from 'app/entities/current-stock-master/current-stock-master-update.component';
import { CurrentStockMasterService } from 'app/entities/current-stock-master/current-stock-master.service';
import { CurrentStockMaster } from 'app/shared/model/current-stock-master.model';

describe('Component Tests', () => {
  describe('CurrentStockMaster Management Update Component', () => {
    let comp: CurrentStockMasterUpdateComponent;
    let fixture: ComponentFixture<CurrentStockMasterUpdateComponent>;
    let service: CurrentStockMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [CurrentStockMasterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CurrentStockMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrentStockMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrentStockMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CurrentStockMaster(123);
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
        const entity = new CurrentStockMaster();
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
