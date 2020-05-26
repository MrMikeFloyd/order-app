import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WebStoreTestModule } from '../../../../test.module';
import { SalesOrderDetailComponent } from 'app/entities/orderService/sales-order/sales-order-detail.component';
import { SalesOrder } from 'app/shared/model/orderService/sales-order.model';

describe('Component Tests', () => {
  describe('SalesOrder Management Detail Component', () => {
    let comp: SalesOrderDetailComponent;
    let fixture: ComponentFixture<SalesOrderDetailComponent>;
    const route = ({ data: of({ salesOrder: new SalesOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SalesOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalesOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load salesOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.salesOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
