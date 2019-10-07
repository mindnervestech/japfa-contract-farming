import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILineSupervisor } from 'app/shared/model/line-supervisor.model';

@Component({
  selector: 'jhi-line-supervisor-detail',
  templateUrl: './line-supervisor-detail.component.html'
})
export class LineSupervisorDetailComponent implements OnInit {
  lineSupervisor: ILineSupervisor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lineSupervisor }) => {
      this.lineSupervisor = lineSupervisor;
    });
  }

  previousState() {
    window.history.back();
  }
}
