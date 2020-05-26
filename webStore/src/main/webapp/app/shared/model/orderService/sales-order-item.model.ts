import { Moment } from 'moment';
import { SalesOrderItemStatus } from 'app/shared/model/enumerations/sales-order-item-status.model';

export interface ISalesOrderItem {
  id?: number;
  name?: string;
  sku?: string;
  taxable?: boolean;
  grosWeight?: number;
  shipped?: Moment;
  delivered?: Moment;
  status?: SalesOrderItemStatus;
  quantity?: number;
  unitPrice?: number;
  amount?: number;
  salesOrderSalesOrderNumber?: string;
  salesOrderId?: number;
}

export class SalesOrderItem implements ISalesOrderItem {
  constructor(
    public id?: number,
    public name?: string,
    public sku?: string,
    public taxable?: boolean,
    public grosWeight?: number,
    public shipped?: Moment,
    public delivered?: Moment,
    public status?: SalesOrderItemStatus,
    public quantity?: number,
    public unitPrice?: number,
    public amount?: number,
    public salesOrderSalesOrderNumber?: string,
    public salesOrderId?: number
  ) {
    this.taxable = this.taxable || false;
  }
}
