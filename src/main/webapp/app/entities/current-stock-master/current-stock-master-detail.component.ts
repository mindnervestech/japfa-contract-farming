import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrentStockMaster } from 'app/shared/model/current-stock-master.model';

@Component({
  selector: 'jhi-current-stock-master-detail',
  templateUrl: './current-stock-master-detail.component.html'
})
export class CurrentStockMasterDetailComponent implements OnInit {
  currentStockMaster: ICurrentStockMaster;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ currentStockMaster }) => {
      this.currentStockMaster = currentStockMaster;
    });
  }

  previousState() {
    window.history.back();
  }
}
