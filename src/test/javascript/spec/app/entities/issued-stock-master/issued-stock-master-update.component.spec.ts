import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { IssuedStockMasterUpdateComponent } from 'app/entities/issued-stock-master/issued-stock-master-update.component';
import { IssuedStockMasterService } from 'app/entities/issued-stock-master/issued-stock-master.service';
import { IssuedStockMaster } from 'app/shared/model/issued-stock-master.model';

describe('Component Tests', () => {
  describe('IssuedStockMaster Management Update Component', () => {
    let comp: IssuedStockMasterUpdateComponent;
    let fixture: ComponentFixture<IssuedStockMasterUpdateComponent>;
    let service: IssuedStockMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [IssuedStockMasterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IssuedStockMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssuedStockMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssuedStockMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssuedStockMaster(123);
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
        const entity = new IssuedStockMaster();
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
