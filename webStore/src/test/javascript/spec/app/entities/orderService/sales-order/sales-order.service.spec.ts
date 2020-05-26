import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SalesOrderService } from 'app/entities/orderService/sales-order/sales-order.service';
import { ISalesOrder, SalesOrder } from 'app/shared/model/orderService/sales-order.model';
import { SalesOrderStatus } from 'app/shared/model/enumerations/sales-order-status.model';

describe('Service Tests', () => {
  describe('SalesOrder Service', () => {
    let injector: TestBed;
    let service: SalesOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: ISalesOrder;
    let expectedResult: ISalesOrder | ISalesOrder[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SalesOrderService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SalesOrder(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, currentDate, currentDate, SalesOrderStatus.PENDING);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            placed: currentDate.format(DATE_TIME_FORMAT),
            cancelled: currentDate.format(DATE_TIME_FORMAT),
            shipped: currentDate.format(DATE_TIME_FORMAT),
            completed: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SalesOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            placed: currentDate.format(DATE_TIME_FORMAT),
            cancelled: currentDate.format(DATE_TIME_FORMAT),
            shipped: currentDate.format(DATE_TIME_FORMAT),
            completed: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            placed: currentDate,
            cancelled: currentDate,
            shipped: currentDate,
            completed: currentDate,
          },
          returnedFromService
        );

        service.create(new SalesOrder()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SalesOrder', () => {
        const returnedFromService = Object.assign(
          {
            salesOrderNumber: 'BBBBBB',
            customerId: 'BBBBBB',
            placed: currentDate.format(DATE_TIME_FORMAT),
            cancelled: currentDate.format(DATE_TIME_FORMAT),
            shipped: currentDate.format(DATE_TIME_FORMAT),
            completed: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            placed: currentDate,
            cancelled: currentDate,
            shipped: currentDate,
            completed: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SalesOrder', () => {
        const returnedFromService = Object.assign(
          {
            salesOrderNumber: 'BBBBBB',
            customerId: 'BBBBBB',
            placed: currentDate.format(DATE_TIME_FORMAT),
            cancelled: currentDate.format(DATE_TIME_FORMAT),
            shipped: currentDate.format(DATE_TIME_FORMAT),
            completed: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            placed: currentDate,
            cancelled: currentDate,
            shipped: currentDate,
            completed: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SalesOrder', () => {
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
