import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { IssuedStockMasterDetailComponent } from 'app/entities/issued-stock-master/issued-stock-master-detail.component';
import { IssuedStockMaster } from 'app/shared/model/issued-stock-master.model';

describe('Component Tests', () => {
  describe('IssuedStockMaster Management Detail Component', () => {
    let comp: IssuedStockMasterDetailComponent;
    let fixture: ComponentFixture<IssuedStockMasterDetailComponent>;
    const route = ({ data: of({ issuedStockMaster: new IssuedStockMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [IssuedStockMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IssuedStockMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssuedStockMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.issuedStockMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
