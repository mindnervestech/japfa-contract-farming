import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISAPMaster, SAPMaster } from 'app/shared/model/sap-master.model';
import { SAPMasterService } from './sap-master.service';

@Component({
  selector: 'jhi-sap-master-update',
  templateUrl: './sap-master-update.component.html'
})
export class SAPMasterUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    farmerName: [],
    farmerID: [],
    branchCode: [],
    flockNumber: [],
    addressOfFarmer: [],
    itemCode: [],
    quantity: [],
    pONumber: []
  });

  constructor(protected sAPMasterService: SAPMasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sAPMaster }) => {
      this.updateForm(sAPMaster);
    });
  }

  updateForm(sAPMaster: ISAPMaster) {
    this.editForm.patchValue({
      id: sAPMaster.id,
      farmerName: sAPMaster.farmerName,
      farmerID: sAPMaster.farmerID,
      branchCode: sAPMaster.branchCode,
      flockNumber: sAPMaster.flockNumber,
      addressOfFarmer: sAPMaster.addressOfFarmer,
      itemCode: sAPMaster.itemCode,
      quantity: sAPMaster.quantity,
      pONumber: sAPMaster.pONumber
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sAPMaster = this.createFromForm();
    if (sAPMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.sAPMasterService.update(sAPMaster));
    } else {
      this.subscribeToSaveResponse(this.sAPMasterService.create(sAPMaster));
    }
  }

  private createFromForm(): ISAPMaster {
    return {
      ...new SAPMaster(),
      id: this.editForm.get(['id']).value,
      farmerName: this.editForm.get(['farmerName']).value,
      farmerID: this.editForm.get(['farmerID']).value,
      branchCode: this.editForm.get(['branchCode']).value,
      flockNumber: this.editForm.get(['flockNumber']).value,
      addressOfFarmer: this.editForm.get(['addressOfFarmer']).value,
      itemCode: this.editForm.get(['itemCode']).value,
      quantity: this.editForm.get(['quantity']).value,
      pONumber: this.editForm.get(['pONumber']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISAPMaster>>) {
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
