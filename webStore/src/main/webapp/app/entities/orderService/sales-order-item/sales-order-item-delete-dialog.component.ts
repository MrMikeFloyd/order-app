import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';
import { SalesOrderItemService } from './sales-order-item.service';

@Component({
  templateUrl: './sales-order-item-delete-dialog.component.html',
})
export class SalesOrderItemDeleteDialogComponent {
  salesOrderItem?: ISalesOrderItem;

  constructor(
    protected salesOrderItemService: SalesOrderItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesOrderItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('salesOrderItemListModification');
      this.activeModal.close();
    });
  }
}
