import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IMrn, Mrn } from 'app/shared/model/mrn.model';
import { MrnService } from './mrn.service';

@Component({
  selector: 'jhi-mrn-update',
  templateUrl: './mrn-update.component.html'
})
export class MrnUpdateComponent implements OnInit {
  isSaving: boolean;
  dCDateDp: any;
  postingDateDp: any;

  editForm = this.fb.group({
    id: [],
    vehicleNumber: [],
    dCDate: [],
    dCNumber: [],
    postingDate: [],
    pONumber: [],
    itemNumber: [],
    avgWeight: [],
    condition: [],
    createdBy: [],
    itemRecieved: [],
    flockNumber: []
  });

  constructor(protected mrnService: MrnService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ mrn }) => {
      this.updateForm(mrn);
    });
  }

  updateForm(mrn: IMrn) {
    this.editForm.patchValue({
      id: mrn.id,
      vehicleNumber: mrn.vehicleNumber,
      dCDate: mrn.dCDate,
      dCNumber: mrn.dCNumber,
      postingDate: mrn.postingDate,
      pONumber: mrn.pONumber,
      itemNumber: mrn.itemNumber,
      avgWeight: mrn.avgWeight,
      condition: mrn.condition,
      createdBy: mrn.createdBy,
      itemRecieved: mrn.itemRecieved,
      flockNumber: mrn.flockNumber
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const mrn = this.createFromForm();
    if (mrn.id !== undefined) {
      this.subscribeToSaveResponse(this.mrnService.update(mrn));
    } else {
      this.subscribeToSaveResponse(this.mrnService.create(mrn));
    }
  }

  private createFromForm(): IMrn {
    return {
      ...new Mrn(),
      id: this.editForm.get(['id']).value,
      vehicleNumber: this.editForm.get(['vehicleNumber']).value,
      dCDate: this.editForm.get(['dCDate']).value,
      dCNumber: this.editForm.get(['dCNumber']).value,
      postingDate: this.editForm.get(['postingDate']).value,
      pONumber: this.editForm.get(['pONumber']).value,
      itemNumber: this.editForm.get(['itemNumber']).value,
      avgWeight: this.editForm.get(['avgWeight']).value,
      condition: this.editForm.get(['condition']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      itemRecieved: this.editForm.get(['itemRecieved']).value,
      flockNumber: this.editForm.get(['flockNumber']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMrn>>) {
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
