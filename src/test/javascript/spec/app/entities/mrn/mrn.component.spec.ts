import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { MrnComponent } from 'app/entities/mrn/mrn.component';
import { MrnService } from 'app/entities/mrn/mrn.service';
import { Mrn } from 'app/shared/model/mrn.model';

describe('Component Tests', () => {
  describe('Mrn Management Component', () => {
    let comp: MrnComponent;
    let fixture: ComponentFixture<MrnComponent>;
    let service: MrnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [MrnComponent],
        providers: []
      })
        .overrideTemplate(MrnComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MrnComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MrnService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mrn(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mrns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
