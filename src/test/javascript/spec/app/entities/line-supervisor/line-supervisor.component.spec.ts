import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MobileCfTestModule } from '../../../test.module';
import { LineSupervisorComponent } from 'app/entities/line-supervisor/line-supervisor.component';
import { LineSupervisorService } from 'app/entities/line-supervisor/line-supervisor.service';
import { LineSupervisor } from 'app/shared/model/line-supervisor.model';

describe('Component Tests', () => {
  describe('LineSupervisor Management Component', () => {
    let comp: LineSupervisorComponent;
    let fixture: ComponentFixture<LineSupervisorComponent>;
    let service: LineSupervisorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [LineSupervisorComponent],
        providers: []
      })
        .overrideTemplate(LineSupervisorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineSupervisorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineSupervisorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LineSupervisor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lineSupervisors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
