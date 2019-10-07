import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { DailyRecordingComponent } from 'app/entities/daily-recording/daily-recording.component';
import { DailyRecordingService } from 'app/entities/daily-recording/daily-recording.service';
import { DailyRecording } from 'app/shared/model/daily-recording.model';

describe('Component Tests', () => {
  describe('DailyRecording Management Component', () => {
    let comp: DailyRecordingComponent;
    let fixture: ComponentFixture<DailyRecordingComponent>;
    let service: DailyRecordingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [DailyRecordingComponent],
        providers: []
      })
        .overrideTemplate(DailyRecordingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyRecordingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyRecordingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DailyRecording(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dailyRecordings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
