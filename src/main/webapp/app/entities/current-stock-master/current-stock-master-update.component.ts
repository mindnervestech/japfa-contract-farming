import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICurrentStockMaster, CurrentStockMaster } from 'app/shared/model/current-stock-master.model';
import { CurrentStockMasterService } from './current-stock-master.service';

@Component({
  selector: 'jhi-current-stock-master-update',
  templateUrl: './current-stock-master-update.component.html'
})
export class CurrentStockMasterUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    flockNumber: [],
    materialCode: [],
    materialName: [],
    stockInHand: []
  });

  constructor(
    protected currentStockMasterService: CurrentStockMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ currentStockMaster }) => {
      this.updateForm(currentStockMaster);
    });
  }

  updateForm(currentStockMaster: ICurrentStockMaster) {
    this.editForm.patchValue({
      id: currentStockMaster.id,
      flockNumber: currentStockMaster.flockNumber,
      materialCode: currentStockMaster.materialCode,
      materialName: currentStockMaster.materialName,
      stockInHand: currentStockMaster.stockInHand
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const currentStockMaster = this.createFromForm();
    if (currentStockMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.currentStockMasterService.update(currentStockMaster));
    } else {
      this.subscribeToSaveResponse(this.currentStockMasterService.create(currentStockMaster));
    }
  }

  private createFromForm(): ICurrentStockMaster {
    return {
      ...new CurrentStockMaster(),
      id: this.editForm.get(['id']).value,
      flockNumber: this.editForm.get(['flockNumber']).value,
      materialCode: this.editForm.get(['materialCode']).value,
      materialName: this.editForm.get(['materialName']).value,
      stockInHand: this.editForm.get(['stockInHand']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrentStockMaster>>) {
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
