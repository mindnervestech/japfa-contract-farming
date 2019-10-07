import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { SAPMasterComponent } from 'app/entities/sap-master/sap-master.component';
import { SAPMasterService } from 'app/entities/sap-master/sap-master.service';
import { SAPMaster } from 'app/shared/model/sap-master.model';

describe('Component Tests', () => {
  describe('SAPMaster Management Component', () => {
    let comp: SAPMasterComponent;
    let fixture: ComponentFixture<SAPMasterComponent>;
    let service: SAPMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [SAPMasterComponent],
        providers: []
      })
        .overrideTemplate(SAPMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SAPMasterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SAPMasterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SAPMaster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sAPMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
