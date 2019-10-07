import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { DailyRecordingUpdateComponent } from 'app/entities/daily-recording/daily-recording-update.component';
import { DailyRecordingService } from 'app/entities/daily-recording/daily-recording.service';
import { DailyRecording } from 'app/shared/model/daily-recording.model';

describe('Component Tests', () => {
  describe('DailyRecording Management Update Component', () => {
    let comp: DailyRecordingUpdateComponent;
    let fixture: ComponentFixture<DailyRecordingUpdateComponent>;
    let service: DailyRecordingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [DailyRecordingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DailyRecordingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyRecordingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyRecordingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DailyRecording(123);
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
        const entity = new DailyRecording();
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
