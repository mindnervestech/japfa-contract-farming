import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDailyRecording } from 'app/shared/model/daily-recording.model';

@Component({
  selector: 'jhi-daily-recording-detail',
  templateUrl: './daily-recording-detail.component.html'
})
export class DailyRecordingDetailComponent implements OnInit {
  dailyRecording: IDailyRecording;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dailyRecording }) => {
      this.dailyRecording = dailyRecording;
    });
  }

  previousState() {
    window.history.back();
  }
}
