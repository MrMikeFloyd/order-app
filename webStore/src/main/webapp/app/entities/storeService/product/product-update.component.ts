import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduct, Product } from 'app/shared/model/storeService/product.model';
import { ProductService } from './product.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2)]],
    sku: [null, [Validators.required, Validators.minLength(6)]],
    description: [],
    srp: [],
    taxable: [],
    salesUnit: [],
    salesQuantity: [],
    status: [],
    grosWeight: [],
    netWeight: [],
    length: [],
    width: [],
    height: [],
  });

  constructor(protected productService: ProductService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      sku: product.sku,
      description: product.description,
      srp: product.srp,
      taxable: product.taxable,
      salesUnit: product.salesUnit,
      salesQuantity: product.salesQuantity,
      status: product.status,
      grosWeight: product.grosWeight,
      netWeight: product.netWeight,
      length: product.length,
      width: product.width,
      height: product.height,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      sku: this.editForm.get(['sku'])!.value,
      description: this.editForm.get(['description'])!.value,
      srp: this.editForm.get(['srp'])!.value,
      taxable: this.editForm.get(['taxable'])!.value,
      salesUnit: this.editForm.get(['salesUnit'])!.value,
      salesQuantity: this.editForm.get(['salesQuantity'])!.value,
      status: this.editForm.get(['status'])!.value,
      grosWeight: this.editForm.get(['grosWeight'])!.value,
      netWeight: this.editForm.get(['netWeight'])!.value,
      length: this.editForm.get(['length'])!.value,
      width: this.editForm.get(['width'])!.value,
      height: this.editForm.get(['height'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
