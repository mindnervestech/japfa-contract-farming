import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFarmerMaster } from 'app/shared/model/farmer-master.model';

@Component({
  selector: 'jhi-farmer-master-detail',
  templateUrl: './farmer-master-detail.component.html'
})
export class FarmerMasterDetailComponent implements OnInit {
  farmerMaster: IFarmerMaster;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ farmerMaster }) => {
      this.farmerMaster = farmerMaster;
    });
  }

  previousState() {
    window.history.back();
  }
}
