import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { FarmerMasterComponent } from 'app/entities/farmer-master/farmer-master.component';
import { FarmerMasterService } from 'app/entities/farmer-master/farmer-master.service';
import { FarmerMaster } from 'app/shared/model/farmer-master.model';

describe('Component Tests', () => {
  describe('FarmerMaster Management Component', () => {
    let comp: FarmerMasterComponent;
    let fixture: ComponentFixture<FarmerMasterComponent>;
    let service: FarmerMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [FarmerMasterComponent],
        providers: []
      })
        .overrideTemplate(FarmerMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FarmerMasterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FarmerMasterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FarmerMaster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.farmerMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
