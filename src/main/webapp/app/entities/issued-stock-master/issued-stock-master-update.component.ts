import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IIssuedStockMaster, IssuedStockMaster } from 'app/shared/model/issued-stock-master.model';
import { IssuedStockMasterService } from './issued-stock-master.service';

@Component({
  selector: 'jhi-issued-stock-master-update',
  templateUrl: './issued-stock-master-update.component.html'
})
export class IssuedStockMasterUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    flockNumber: [],
    materialCode: [],
    materialName: [],
    stockIssued: []
  });

  constructor(
    protected issuedStockMasterService: IssuedStockMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ issuedStockMaster }) => {
      this.updateForm(issuedStockMaster);
    });
  }

  updateForm(issuedStockMaster: IIssuedStockMaster) {
    this.editForm.patchValue({
      id: issuedStockMaster.id,
      flockNumber: issuedStockMaster.flockNumber,
      materialCode: issuedStockMaster.materialCode,
      materialName: issuedStockMaster.materialName,
      stockIssued: issuedStockMaster.stockIssued
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const issuedStockMaster = this.createFromForm();
    if (issuedStockMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.issuedStockMasterService.update(issuedStockMaster));
    } else {
      this.subscribeToSaveResponse(this.issuedStockMasterService.create(issuedStockMaster));
    }
  }

  private createFromForm(): IIssuedStockMaster {
    return {
      ...new IssuedStockMaster(),
      id: this.editForm.get(['id']).value,
      flockNumber: this.editForm.get(['flockNumber']).value,
      materialCode: this.editForm.get(['materialCode']).value,
      materialName: this.editForm.get(['materialName']).value,
      stockIssued: this.editForm.get(['stockIssued']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssuedStockMaster>>) {
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
