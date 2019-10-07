import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { LineSupervisorDetailComponent } from 'app/entities/line-supervisor/line-supervisor-detail.component';
import { LineSupervisor } from 'app/shared/model/line-supervisor.model';

describe('Component Tests', () => {
  describe('LineSupervisor Management Detail Component', () => {
    let comp: LineSupervisorDetailComponent;
    let fixture: ComponentFixture<LineSupervisorDetailComponent>;
    const route = ({ data: of({ lineSupervisor: new LineSupervisor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [LineSupervisorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LineSupervisorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineSupervisorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lineSupervisor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
