import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPurchaseOrder, PurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from './purchase-order.service';

@Component({
  selector: 'jhi-purchase-order-update',
  templateUrl: './purchase-order-update.component.html'
})
export class PurchaseOrderUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    farmerName: [],
    flockNumber: [],
    pONumber: [],
    itemName: [],
    itemID: [],
    quantity: [],
    supplierName: [],
    transpoterName: []
  });

  constructor(protected purchaseOrderService: PurchaseOrderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
      this.updateForm(purchaseOrder);
    });
  }

  updateForm(purchaseOrder: IPurchaseOrder) {
    this.editForm.patchValue({
      id: purchaseOrder.id,
      farmerName: purchaseOrder.farmerName,
      flockNumber: purchaseOrder.flockNumber,
      pONumber: purchaseOrder.pONumber,
      itemName: purchaseOrder.itemName,
      itemID: purchaseOrder.itemID,
      quantity: purchaseOrder.quantity,
      supplierName: purchaseOrder.supplierName,
      transpoterName: purchaseOrder.transpoterName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const purchaseOrder = this.createFromForm();
    if (purchaseOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrderService.update(purchaseOrder));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrderService.create(purchaseOrder));
    }
  }

  private createFromForm(): IPurchaseOrder {
    return {
      ...new PurchaseOrder(),
      id: this.editForm.get(['id']).value,
      farmerName: this.editForm.get(['farmerName']).value,
      flockNumber: this.editForm.get(['flockNumber']).value,
      pONumber: this.editForm.get(['pONumber']).value,
      itemName: this.editForm.get(['itemName']).value,
      itemID: this.editForm.get(['itemID']).value,
      quantity: this.editForm.get(['quantity']).value,
      supplierName: this.editForm.get(['supplierName']).value,
      transpoterName: this.editForm.get(['transpoterName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrder>>) {
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
