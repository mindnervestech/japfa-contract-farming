import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { FarmerMasterService } from 'app/entities/farmer-master/farmer-master.service';
import { IFarmerMaster, FarmerMaster } from 'app/shared/model/farmer-master.model';

describe('Service Tests', () => {
  describe('FarmerMaster Service', () => {
    let injector: TestBed;
    let service: FarmerMasterService;
    let httpMock: HttpTestingController;
    let elemDefault: IFarmerMaster;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FarmerMasterService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FarmerMaster(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a FarmerMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new FarmerMaster(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a FarmerMaster', () => {
        const returnedFromService = Object.assign(
          {
            farmerName: 'BBBBBB',
            farmerID: 'BBBBBB',
            flockNumber: 'BBBBBB',
            addressOfFarmer: 'BBBBBB',
            lineSupervisorName: 'BBBBBB',
            lineSupervisorID: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of FarmerMaster', () => {
        const returnedFromService = Object.assign(
          {
            farmerName: 'BBBBBB',
            farmerID: 'BBBBBB',
            flockNumber: 'BBBBBB',
            addressOfFarmer: 'BBBBBB',
            lineSupervisorName: 'BBBBBB',
            lineSupervisorID: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a FarmerMaster', () => {
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
