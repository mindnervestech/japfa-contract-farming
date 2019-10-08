import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { FarmerMasterDetailComponent } from 'app/entities/farmer-master/farmer-master-detail.component';
import { FarmerMaster } from 'app/shared/model/farmer-master.model';

describe('Component Tests', () => {
  describe('FarmerMaster Management Detail Component', () => {
    let comp: FarmerMasterDetailComponent;
    let fixture: ComponentFixture<FarmerMasterDetailComponent>;
    const route = ({ data: of({ farmerMaster: new FarmerMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [FarmerMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FarmerMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FarmerMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.farmerMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
