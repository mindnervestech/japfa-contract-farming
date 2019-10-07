import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISAPMaster } from 'app/shared/model/sap-master.model';

@Component({
  selector: 'jhi-sap-master-detail',
  templateUrl: './sap-master-detail.component.html'
})
export class SAPMasterDetailComponent implements OnInit {
  sAPMaster: ISAPMaster;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sAPMaster }) => {
      this.sAPMaster = sAPMaster;
    });
  }

  previousState() {
    window.history.back();
  }
}
