import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';
import { SalesOrderItemService } from './sales-order-item.service';
import { SalesOrderItemDeleteDialogComponent } from './sales-order-item-delete-dialog.component';

@Component({
  selector: 'jhi-sales-order-item',
  templateUrl: './sales-order-item.component.html',
})
export class SalesOrderItemComponent implements OnInit, OnDestroy {
  salesOrderItems?: ISalesOrderItem[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected salesOrderItemService: SalesOrderItemService,
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
      this.salesOrderItemService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ISalesOrderItem[]>) => (this.salesOrderItems = res.body || []));
      return;
    }

    this.salesOrderItemService.query().subscribe((res: HttpResponse<ISalesOrderItem[]>) => (this.salesOrderItems = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSalesOrderItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISalesOrderItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSalesOrderItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('salesOrderItemListModification', () => this.loadAll());
  }

  delete(salesOrderItem: ISalesOrderItem): void {
    const modalRef = this.modalService.open(SalesOrderItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesOrderItem = salesOrderItem;
  }
}
