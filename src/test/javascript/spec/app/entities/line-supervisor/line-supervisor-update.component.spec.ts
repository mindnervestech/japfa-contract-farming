import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { LineSupervisorUpdateComponent } from 'app/entities/line-supervisor/line-supervisor-update.component';
import { LineSupervisorService } from 'app/entities/line-supervisor/line-supervisor.service';
import { LineSupervisor } from 'app/shared/model/line-supervisor.model';

describe('Component Tests', () => {
  describe('LineSupervisor Management Update Component', () => {
    let comp: LineSupervisorUpdateComponent;
    let fixture: ComponentFixture<LineSupervisorUpdateComponent>;
    let service: LineSupervisorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [LineSupervisorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LineSupervisorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineSupervisorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineSupervisorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LineSupervisor(123);
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
        const entity = new LineSupervisor();
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
