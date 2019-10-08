import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DailyRecordingService } from 'app/entities/daily-recording/daily-recording.service';
import { IDailyRecording, DailyRecording } from 'app/shared/model/daily-recording.model';

describe('Service Tests', () => {
  describe('DailyRecording Service', () => {
    let injector: TestBed;
    let service: DailyRecordingService;
    let httpMock: HttpTestingController;
    let elemDefault: IDailyRecording;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DailyRecordingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DailyRecording(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdOn: currentDate.format(DATE_FORMAT)
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

      it('should create a DailyRecording', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdOn: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdOn: currentDate
          },
          returnedFromService
        );
        service
          .create(new DailyRecording(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DailyRecording', () => {
        const returnedFromService = Object.assign(
          {
            flockNumber: 'BBBBBB',
            materialCode: 'BBBBBB',
            chiksSamplingWeight: 1,
            chiksCondition: 'BBBBBB',
            quantity: 'BBBBBB',
            comment: 'BBBBBB',
            createdBy: 1,
            createdOn: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdOn: currentDate
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

      it('should return a list of DailyRecording', () => {
        const returnedFromService = Object.assign(
          {
            flockNumber: 'BBBBBB',
            materialCode: 'BBBBBB',
            chiksSamplingWeight: 1,
            chiksCondition: 'BBBBBB',
            quantity: 'BBBBBB',
            comment: 'BBBBBB',
            createdBy: 1,
            createdOn: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdOn: currentDate
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

      it('should delete a DailyRecording', () => {
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
