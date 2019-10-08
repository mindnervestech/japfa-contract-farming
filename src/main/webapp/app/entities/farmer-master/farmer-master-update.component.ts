import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFarmerMaster, FarmerMaster } from 'app/shared/model/farmer-master.model';
import { FarmerMasterService } from './farmer-master.service';

@Component({
  selector: 'jhi-farmer-master-update',
  templateUrl: './farmer-master-update.component.html'
})
export class FarmerMasterUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    farmerName: [],
    farmerID: [],
    flockNumber: [],
    addressOfFarmer: [],
    lineSupervisorName: [],
    lineSupervisorID: []
  });

  constructor(protected farmerMasterService: FarmerMasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ farmerMaster }) => {
      this.updateForm(farmerMaster);
    });
  }

  updateForm(farmerMaster: IFarmerMaster) {
    this.editForm.patchValue({
      id: farmerMaster.id,
      farmerName: farmerMaster.farmerName,
      farmerID: farmerMaster.farmerID,
      flockNumber: farmerMaster.flockNumber,
      addressOfFarmer: farmerMaster.addressOfFarmer,
      lineSupervisorName: farmerMaster.lineSupervisorName,
      lineSupervisorID: farmerMaster.lineSupervisorID
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const farmerMaster = this.createFromForm();
    if (farmerMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.farmerMasterService.update(farmerMaster));
    } else {
      this.subscribeToSaveResponse(this.farmerMasterService.create(farmerMaster));
    }
  }

  private createFromForm(): IFarmerMaster {
    return {
      ...new FarmerMaster(),
      id: this.editForm.get(['id']).value,
      farmerName: this.editForm.get(['farmerName']).value,
      farmerID: this.editForm.get(['farmerID']).value,
      flockNumber: this.editForm.get(['flockNumber']).value,
      addressOfFarmer: this.editForm.get(['addressOfFarmer']).value,
      lineSupervisorName: this.editForm.get(['lineSupervisorName']).value,
      lineSupervisorID: this.editForm.get(['lineSupervisorID']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFarmerMaster>>) {
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
