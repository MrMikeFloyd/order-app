import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesOrder } from 'app/shared/model/orderService/sales-order.model';
import { SalesOrderService } from './sales-order.service';
import { SalesOrderDeleteDialogComponent } from './sales-order-delete-dialog.component';

@Component({
  selector: 'jhi-sales-order',
  templateUrl: './sales-order.component.html',
})
export class SalesOrderComponent implements OnInit, OnDestroy {
  salesOrders?: ISalesOrder[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected salesOrderService: SalesOrderService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.salesOrderService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ISalesOrder[]>) => (this.salesOrders = res.body || []));
      return;
    }

    this.salesOrderService.query().subscribe((res: HttpResponse<ISalesOrder[]>) => (this.salesOrders = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSalesOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISalesOrder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSalesOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('salesOrderListModification', () => this.loadAll());
  }

  delete(salesOrder: ISalesOrder): void {
    const modalRef = this.modalService.open(SalesOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesOrder = salesOrder;
  }
}
