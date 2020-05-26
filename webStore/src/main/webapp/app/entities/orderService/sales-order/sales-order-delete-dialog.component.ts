import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalesOrder } from 'app/shared/model/orderService/sales-order.model';
import { SalesOrderService } from './sales-order.service';

@Component({
  templateUrl: './sales-order-delete-dialog.component.html',
})
export class SalesOrderDeleteDialogComponent {
  salesOrder?: ISalesOrder;

  constructor(
    protected salesOrderService: SalesOrderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('salesOrderListModification');
      this.activeModal.close();
    });
  }
}
