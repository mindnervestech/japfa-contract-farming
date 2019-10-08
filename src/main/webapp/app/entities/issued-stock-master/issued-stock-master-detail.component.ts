import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssuedStockMaster } from 'app/shared/model/issued-stock-master.model';

@Component({
  selector: 'jhi-issued-stock-master-detail',
  templateUrl: './issued-stock-master-detail.component.html'
})
export class IssuedStockMasterDetailComponent implements OnInit {
  issuedStockMaster: IIssuedStockMaster;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ issuedStockMaster }) => {
      this.issuedStockMaster = issuedStockMaster;
    });
  }

  previousState() {
    window.history.back();
  }
}
