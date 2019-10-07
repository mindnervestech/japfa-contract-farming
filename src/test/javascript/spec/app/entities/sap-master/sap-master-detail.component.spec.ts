import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { SAPMasterDetailComponent } from 'app/entities/sap-master/sap-master-detail.component';
import { SAPMaster } from 'app/shared/model/sap-master.model';

describe('Component Tests', () => {
  describe('SAPMaster Management Detail Component', () => {
    let comp: SAPMasterDetailComponent;
    let fixture: ComponentFixture<SAPMasterDetailComponent>;
    const route = ({ data: of({ sAPMaster: new SAPMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [SAPMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SAPMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SAPMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sAPMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
