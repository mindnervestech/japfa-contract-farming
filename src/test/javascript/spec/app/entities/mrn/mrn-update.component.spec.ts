import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { MrnUpdateComponent } from 'app/entities/mrn/mrn-update.component';
import { MrnService } from 'app/entities/mrn/mrn.service';
import { Mrn } from 'app/shared/model/mrn.model';

describe('Component Tests', () => {
  describe('Mrn Management Update Component', () => {
    let comp: MrnUpdateComponent;
    let fixture: ComponentFixture<MrnUpdateComponent>;
    let service: MrnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [MrnUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MrnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MrnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MrnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mrn(123);
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
        const entity = new Mrn();
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
