import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IDailyRecording, DailyRecording } from 'app/shared/model/daily-recording.model';
import { DailyRecordingService } from './daily-recording.service';

@Component({
  selector: 'jhi-daily-recording-update',
  templateUrl: './daily-recording-update.component.html'
})
export class DailyRecordingUpdateComponent implements OnInit {
  isSaving: boolean;
  createdOnDp: any;

  editForm = this.fb.group({
    id: [],
    flockNumber: [],
    itemCode: [],
    quantity: [],
    comment: [],
    createdBy: [],
    createdOn: []
  });

  constructor(protected dailyRecordingService: DailyRecordingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dailyRecording }) => {
      this.updateForm(dailyRecording);
    });
  }

  updateForm(dailyRecording: IDailyRecording) {
    this.editForm.patchValue({
      id: dailyRecording.id,
      flockNumber: dailyRecording.flockNumber,
      itemCode: dailyRecording.itemCode,
      quantity: dailyRecording.quantity,
      comment: dailyRecording.comment,
      createdBy: dailyRecording.createdBy,
      createdOn: dailyRecording.createdOn
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dailyRecording = this.createFromForm();
    if (dailyRecording.id !== undefined) {
      this.subscribeToSaveResponse(this.dailyRecordingService.update(dailyRecording));
    } else {
      this.subscribeToSaveResponse(this.dailyRecordingService.create(dailyRecording));
    }
  }

  private createFromForm(): IDailyRecording {
    return {
      ...new DailyRecording(),
      id: this.editForm.get(['id']).value,
      flockNumber: this.editForm.get(['flockNumber']).value,
      itemCode: this.editForm.get(['itemCode']).value,
      quantity: this.editForm.get(['quantity']).value,
      comment: this.editForm.get(['comment']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdOn: this.editForm.get(['createdOn']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyRecording>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
