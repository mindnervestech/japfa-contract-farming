import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILineSupervisor, LineSupervisor } from 'app/shared/model/line-supervisor.model';
import { LineSupervisorService } from './line-supervisor.service';

@Component({
  selector: 'jhi-line-supervisor-update',
  templateUrl: './line-supervisor-update.component.html'
})
export class LineSupervisorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    branchCode: []
  });

  constructor(protected lineSupervisorService: LineSupervisorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lineSupervisor }) => {
      this.updateForm(lineSupervisor);
    });
  }

  updateForm(lineSupervisor: ILineSupervisor) {
    this.editForm.patchValue({
      id: lineSupervisor.id,
      name: lineSupervisor.name,
      branchCode: lineSupervisor.branchCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lineSupervisor = this.createFromForm();
    if (lineSupervisor.id !== undefined) {
      this.subscribeToSaveResponse(this.lineSupervisorService.update(lineSupervisor));
    } else {
      this.subscribeToSaveResponse(this.lineSupervisorService.create(lineSupervisor));
    }
  }

  private createFromForm(): ILineSupervisor {
    return {
      ...new LineSupervisor(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      branchCode: this.editForm.get(['branchCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILineSupervisor>>) {
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
