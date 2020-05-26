import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebStoreTestModule } from '../../../../test.module';
import { SalesOrderItemComponent } from 'app/entities/orderService/sales-order-item/sales-order-item.component';
import { SalesOrderItemService } from 'app/entities/orderService/sales-order-item/sales-order-item.service';
import { SalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';

describe('Component Tests', () => {
  describe('SalesOrderItem Management Component', () => {
    let comp: SalesOrderItemComponent;
    let fixture: ComponentFixture<SalesOrderItemComponent>;
    let service: SalesOrderItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderItemComponent],
      })
        .overrideTemplate(SalesOrderItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesOrderItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesOrderItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SalesOrderItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.salesOrderItems && comp.salesOrderItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
