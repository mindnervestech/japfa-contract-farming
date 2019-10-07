import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PurchaseOrderService } from 'app/entities/purchase-order/purchase-order.service';
import { IPurchaseOrder, PurchaseOrder } from 'app/shared/model/purchase-order.model';

describe('Service Tests', () => {
  describe('PurchaseOrder Service', () => {
    let injector: TestBed;
    let service: PurchaseOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IPurchaseOrder;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PurchaseOrderService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PurchaseOrder(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA');
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

      it('should create a PurchaseOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PurchaseOrder(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PurchaseOrder', () => {
        const returnedFromService = Object.assign(
          {
            farmerName: 'BBBBBB',
            flockNumber: 'BBBBBB',
            pONumber: 'BBBBBB',
            itemName: 'BBBBBB',
            itemID: 'BBBBBB',
            quantity: 1,
            supplierName: 'BBBBBB',
            transpoterName: 'BBBBBB'
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

      it('should return a list of PurchaseOrder', () => {
        const returnedFromService = Object.assign(
          {
            farmerName: 'BBBBBB',
            flockNumber: 'BBBBBB',
            pONumber: 'BBBBBB',
            itemName: 'BBBBBB',
            itemID: 'BBBBBB',
            quantity: 1,
            supplierName: 'BBBBBB',
            transpoterName: 'BBBBBB'
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

      it('should delete a PurchaseOrder', () => {
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
