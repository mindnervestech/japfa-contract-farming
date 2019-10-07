import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMrn } from 'app/shared/model/mrn.model';

@Component({
  selector: 'jhi-mrn-detail',
  templateUrl: './mrn-detail.component.html'
})
export class MrnDetailComponent implements OnInit {
  mrn: IMrn;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mrn }) => {
      this.mrn = mrn;
    });
  }

  previousState() {
    window.history.back();
  }
}
