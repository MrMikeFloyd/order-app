import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WebStoreTestModule } from '../../../../test.module';
import { SalesOrderItemDetailComponent } from 'app/entities/orderService/sales-order-item/sales-order-item-detail.component';
import { SalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';

describe('Component Tests', () => {
  describe('SalesOrderItem Management Detail Component', () => {
    let comp: SalesOrderItemDetailComponent;
    let fixture: ComponentFixture<SalesOrderItemDetailComponent>;
    const route = ({ data: of({ salesOrderItem: new SalesOrderItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SalesOrderItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalesOrderItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load salesOrderItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.salesOrderItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
