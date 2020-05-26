import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISalesOrder, SalesOrder } from 'app/shared/model/orderService/sales-order.model';
import { SalesOrderService } from './sales-order.service';

@Component({
  selector: 'jhi-sales-order-update',
  templateUrl: './sales-order-update.component.html',
})
export class SalesOrderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    salesOrderNumber: [],
    customerId: [],
    placed: [],
    cancelled: [],
    shipped: [],
    completed: [],
    status: [],
  });

  constructor(protected salesOrderService: SalesOrderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesOrder }) => {
      if (!salesOrder.id) {
        const today = moment().startOf('day');
        salesOrder.placed = today;
        salesOrder.cancelled = today;
        salesOrder.shipped = today;
        salesOrder.completed = today;
      }

      this.updateForm(salesOrder);
    });
  }

  updateForm(salesOrder: ISalesOrder): void {
    this.editForm.patchValue({
      id: salesOrder.id,
      salesOrderNumber: salesOrder.salesOrderNumber,
      customerId: salesOrder.customerId,
      placed: salesOrder.placed ? salesOrder.placed.format(DATE_TIME_FORMAT) : null,
      cancelled: salesOrder.cancelled ? salesOrder.cancelled.format(DATE_TIME_FORMAT) : null,
      shipped: salesOrder.shipped ? salesOrder.shipped.format(DATE_TIME_FORMAT) : null,
      completed: salesOrder.completed ? salesOrder.completed.format(DATE_TIME_FORMAT) : null,
      status: salesOrder.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesOrder = this.createFromForm();
    if (salesOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.salesOrderService.update(salesOrder));
    } else {
      this.subscribeToSaveResponse(this.salesOrderService.create(salesOrder));
    }
  }

  private createFromForm(): ISalesOrder {
    return {
      ...new SalesOrder(),
      id: this.editForm.get(['id'])!.value,
      salesOrderNumber: this.editForm.get(['salesOrderNumber'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      placed: this.editForm.get(['placed'])!.value ? moment(this.editForm.get(['placed'])!.value, DATE_TIME_FORMAT) : undefined,
      cancelled: this.editForm.get(['cancelled'])!.value ? moment(this.editForm.get(['cancelled'])!.value, DATE_TIME_FORMAT) : undefined,
      shipped: this.editForm.get(['shipped'])!.value ? moment(this.editForm.get(['shipped'])!.value, DATE_TIME_FORMAT) : undefined,
      completed: this.editForm.get(['completed'])!.value ? moment(this.editForm.get(['completed'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesOrder>>): void {
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
}
