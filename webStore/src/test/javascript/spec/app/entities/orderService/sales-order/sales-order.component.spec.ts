import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebStoreTestModule } from '../../../../test.module';
import { SalesOrderComponent } from 'app/entities/orderService/sales-order/sales-order.component';
import { SalesOrderService } from 'app/entities/orderService/sales-order/sales-order.service';
import { SalesOrder } from 'app/shared/model/orderService/sales-order.model';

describe('Component Tests', () => {
  describe('SalesOrder Management Component', () => {
    let comp: SalesOrderComponent;
    let fixture: ComponentFixture<SalesOrderComponent>;
    let service: SalesOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderComponent],
      })
        .overrideTemplate(SalesOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SalesOrder(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.salesOrders && comp.salesOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
