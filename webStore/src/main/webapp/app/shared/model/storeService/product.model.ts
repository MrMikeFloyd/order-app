import { IPhoto } from 'app/shared/model/storeService/photo.model';
import { UnitOfMeasurement } from 'app/shared/model/enumerations/unit-of-measurement.model';
import { ProductStatus } from 'app/shared/model/enumerations/product-status.model';

export interface IProduct {
  id?: number;
  name?: string;
  sku?: string;
  description?: string;
  srp?: number;
  taxable?: boolean;
  salesUnit?: UnitOfMeasurement;
  salesQuantity?: number;
  status?: ProductStatus;
  grosWeight?: number;
  netWeight?: number;
  length?: number;
  width?: number;
  height?: number;
  photos?: IPhoto[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public sku?: string,
    public description?: string,
    public srp?: number,
    public taxable?: boolean,
    public salesUnit?: UnitOfMeasurement,
    public salesQuantity?: number,
    public status?: ProductStatus,
    public grosWeight?: number,
    public netWeight?: number,
    public length?: number,
    public width?: number,
    public height?: number,
    public photos?: IPhoto[]
  ) {
    this.taxable = this.taxable || false;
  }
}
