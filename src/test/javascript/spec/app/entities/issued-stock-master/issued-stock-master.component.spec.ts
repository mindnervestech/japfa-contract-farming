import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { IssuedStockMasterComponent } from 'app/entities/issued-stock-master/issued-stock-master.component';
import { IssuedStockMasterService } from 'app/entities/issued-stock-master/issued-stock-master.service';
import { IssuedStockMaster } from 'app/shared/model/issued-stock-master.model';

describe('Component Tests', () => {
  describe('IssuedStockMaster Management Component', () => {
    let comp: IssuedStockMasterComponent;
    let fixture: ComponentFixture<IssuedStockMasterComponent>;
    let service: IssuedStockMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [IssuedStockMasterComponent],
        providers: []
      })
        .overrideTemplate(IssuedStockMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssuedStockMasterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssuedStockMasterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IssuedStockMaster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.issuedStockMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
