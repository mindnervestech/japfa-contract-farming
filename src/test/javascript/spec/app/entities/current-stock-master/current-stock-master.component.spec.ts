import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { CurrentStockMasterComponent } from 'app/entities/current-stock-master/current-stock-master.component';
import { CurrentStockMasterService } from 'app/entities/current-stock-master/current-stock-master.service';
import { CurrentStockMaster } from 'app/shared/model/current-stock-master.model';

describe('Component Tests', () => {
  describe('CurrentStockMaster Management Component', () => {
    let comp: CurrentStockMasterComponent;
    let fixture: ComponentFixture<CurrentStockMasterComponent>;
    let service: CurrentStockMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [CurrentStockMasterComponent],
        providers: []
      })
        .overrideTemplate(CurrentStockMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrentStockMasterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrentStockMasterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CurrentStockMaster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.currentStockMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
