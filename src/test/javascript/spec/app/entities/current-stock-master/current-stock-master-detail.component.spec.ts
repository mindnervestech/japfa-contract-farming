import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { CurrentStockMasterDetailComponent } from 'app/entities/current-stock-master/current-stock-master-detail.component';
import { CurrentStockMaster } from 'app/shared/model/current-stock-master.model';

describe('Component Tests', () => {
  describe('CurrentStockMaster Management Detail Component', () => {
    let comp: CurrentStockMasterDetailComponent;
    let fixture: ComponentFixture<CurrentStockMasterDetailComponent>;
    const route = ({ data: of({ currentStockMaster: new CurrentStockMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [CurrentStockMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CurrentStockMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurrentStockMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.currentStockMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
