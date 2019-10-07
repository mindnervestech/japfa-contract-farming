import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MrnService } from 'app/entities/mrn/mrn.service';
import { IMrn, Mrn } from 'app/shared/model/mrn.model';

describe('Service Tests', () => {
  describe('Mrn Service', () => {
    let injector: TestBed;
    let service: MrnService;
    let httpMock: HttpTestingController;
    let elemDefault: IMrn;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MrnService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Mrn(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dCDate: currentDate.format(DATE_FORMAT),
            postingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Mrn', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dCDate: currentDate.format(DATE_FORMAT),
            postingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dCDate: currentDate,
            postingDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Mrn(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Mrn', () => {
        const returnedFromService = Object.assign(
          {
            vehicleNumber: 'BBBBBB',
            dCDate: currentDate.format(DATE_FORMAT),
            dCNumber: 'BBBBBB',
            postingDate: currentDate.format(DATE_FORMAT),
            pONumber: 'BBBBBB',
            itemNumber: 'BBBBBB',
            avgWeight: 'BBBBBB',
            condition: 'BBBBBB',
            createdBy: 1,
            itemRecieved: 1,
            flockNumber: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dCDate: currentDate,
            postingDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Mrn', () => {
        const returnedFromService = Object.assign(
          {
            vehicleNumber: 'BBBBBB',
            dCDate: currentDate.format(DATE_FORMAT),
            dCNumber: 'BBBBBB',
            postingDate: currentDate.format(DATE_FORMAT),
            pONumber: 'BBBBBB',
            itemNumber: 'BBBBBB',
            avgWeight: 'BBBBBB',
            condition: 'BBBBBB',
            createdBy: 1,
            itemRecieved: 1,
            flockNumber: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dCDate: currentDate,
            postingDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Mrn', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
