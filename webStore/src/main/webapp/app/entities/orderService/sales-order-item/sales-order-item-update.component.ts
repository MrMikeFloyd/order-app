import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISalesOrderItem, SalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';
import { SalesOrderItemService } from './sales-order-item.service';
import { ISalesOrder } from 'app/shared/model/orderService/sales-order.model';
import { SalesOrderService } from 'app/entities/orderService/sales-order/sales-order.service';

@Component({
  selector: 'jhi-sales-order-item-update',
  templateUrl: './sales-order-item-update.component.html',
})
export class SalesOrderItemUpdateComponent implements OnInit {
  isSaving = false;
  salesorders: ISalesOrder[] = [];
  shippedDp: any;
  deliveredDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    sku: [],
    taxable: [],
    grosWeight: [],
    shipped: [],
    delivered: [],
    status: [],
    quantity: [],
    unitPrice: [],
    amount: [],
    salesOrderId: [],
  });

  constructor(
    protected salesOrderItemService: SalesOrderItemService,
    protected salesOrderService: SalesOrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesOrderItem }) => {
      this.updateForm(salesOrderItem);

      this.salesOrderService.query().subscribe((res: HttpResponse<ISalesOrder[]>) => (this.salesorders = res.body || []));
    });
  }

  updateForm(salesOrderItem: ISalesOrderItem): void {
    this.editForm.patchValue({
      id: salesOrderItem.id,
      name: salesOrderItem.name,
      sku: salesOrderItem.sku,
      taxable: salesOrderItem.taxable,
      grosWeight: salesOrderItem.grosWeight,
      shipped: salesOrderItem.shipped,
      delivered: salesOrderItem.delivered,
      status: salesOrderItem.status,
      quantity: salesOrderItem.quantity,
      unitPrice: salesOrderItem.unitPrice,
      amount: salesOrderItem.amount,
      salesOrderId: salesOrderItem.salesOrderId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesOrderItem = this.createFromForm();
    if (salesOrderItem.id !== undefined) {
      this.subscribeToSaveResponse(this.salesOrderItemService.update(salesOrderItem));
    } else {
      this.subscribeToSaveResponse(this.salesOrderItemService.create(salesOrderItem));
    }
  }

  private createFromForm(): ISalesOrderItem {
    return {
      ...new SalesOrderItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      sku: this.editForm.get(['sku'])!.value,
      taxable: this.editForm.get(['taxable'])!.value,
      grosWeight: this.editForm.get(['grosWeight'])!.value,
      shipped: this.editForm.get(['shipped'])!.value,
      delivered: this.editForm.get(['delivered'])!.value,
      status: this.editForm.get(['status'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      salesOrderId: this.editForm.get(['salesOrderId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesOrderItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISalesOrder): any {
    return item.id;
  }
}
