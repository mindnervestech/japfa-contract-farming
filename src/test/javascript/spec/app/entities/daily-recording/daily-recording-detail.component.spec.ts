import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { DailyRecordingDetailComponent } from 'app/entities/daily-recording/daily-recording-detail.component';
import { DailyRecording } from 'app/shared/model/daily-recording.model';

describe('Component Tests', () => {
  describe('DailyRecording Management Detail Component', () => {
    let comp: DailyRecordingDetailComponent;
    let fixture: ComponentFixture<DailyRecordingDetailComponent>;
    const route = ({ data: of({ dailyRecording: new DailyRecording(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [DailyRecordingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DailyRecordingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyRecordingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dailyRecording).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
