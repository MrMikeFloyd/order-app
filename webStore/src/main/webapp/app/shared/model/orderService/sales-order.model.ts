import { Moment } from 'moment';
import { ISalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';
import { SalesOrderStatus } from 'app/shared/model/enumerations/sales-order-status.model';

export interface ISalesOrder {
  id?: number;
  salesOrderNumber?: string;
  customerId?: string;
  placed?: Moment;
  cancelled?: Moment;
  shipped?: Moment;
  completed?: Moment;
  status?: SalesOrderStatus;
  orderItems?: ISalesOrderItem[];
}

export class SalesOrder implements ISalesOrder {
  constructor(
    public id?: number,
    public salesOrderNumber?: string,
    public customerId?: string,
    public placed?: Moment,
    public cancelled?: Moment,
    public shipped?: Moment,
    public completed?: Moment,
    public status?: SalesOrderStatus,
    public orderItems?: ISalesOrderItem[]
  ) {}
}
