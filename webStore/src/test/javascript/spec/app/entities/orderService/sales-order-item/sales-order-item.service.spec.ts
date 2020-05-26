import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SalesOrderItemService } from 'app/entities/orderService/sales-order-item/sales-order-item.service';
import { ISalesOrderItem, SalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';
import { SalesOrderItemStatus } from 'app/shared/model/enumerations/sales-order-item-status.model';

describe('Service Tests', () => {
  describe('SalesOrderItem Service', () => {
    let injector: TestBed;
    let service: SalesOrderItemService;
    let httpMock: HttpTestingController;
    let elemDefault: ISalesOrderItem;
    let expectedResult: ISalesOrderItem | ISalesOrderItem[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SalesOrderItemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SalesOrderItem(0, 'AAAAAAA', 'AAAAAAA', false, 0, currentDate, currentDate, SalesOrderItemStatus.PENDING, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            shipped: currentDate.format(DATE_FORMAT),
            delivered: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SalesOrderItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            shipped: currentDate.format(DATE_FORMAT),
            delivered: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            shipped: currentDate,
            delivered: currentDate,
          },
          returnedFromService
        );

        service.create(new SalesOrderItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SalesOrderItem', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            sku: 'BBBBBB',
            taxable: true,
            grosWeight: 1,
            shipped: currentDate.format(DATE_FORMAT),
            delivered: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            quantity: 1,
            unitPrice: 1,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            shipped: currentDate,
            delivered: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SalesOrderItem', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            sku: 'BBBBBB',
            taxable: true,
            grosWeight: 1,
            shipped: currentDate.format(DATE_FORMAT),
            delivered: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            quantity: 1,
            unitPrice: 1,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            shipped: currentDate,
            delivered: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SalesOrderItem', () => {
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
