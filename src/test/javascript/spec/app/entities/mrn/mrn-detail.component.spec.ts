import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MobileCfTestModule } from '../../../test.module';
import { MrnDetailComponent } from 'app/entities/mrn/mrn-detail.component';
import { Mrn } from 'app/shared/model/mrn.model';

describe('Component Tests', () => {
  describe('Mrn Management Detail Component', () => {
    let comp: MrnDetailComponent;
    let fixture: ComponentFixture<MrnDetailComponent>;
    const route = ({ data: of({ mrn: new Mrn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MobileCfTestModule],
        declarations: [MrnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MrnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MrnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mrn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
