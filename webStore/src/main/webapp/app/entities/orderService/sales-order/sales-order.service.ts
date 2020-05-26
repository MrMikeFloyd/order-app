import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISalesOrder } from 'app/shared/model/orderService/sales-order.model';

type EntityResponseType = HttpResponse<ISalesOrder>;
type EntityArrayResponseType = HttpResponse<ISalesOrder[]>;

@Injectable({ providedIn: 'root' })
export class SalesOrderService {
  public resourceUrl = SERVER_API_URL + 'services/orderservice/api/sales-orders';
  public resourceSearchUrl = SERVER_API_URL + 'services/orderservice/api/_search/sales-orders';

  constructor(protected http: HttpClient) {}

  create(salesOrder: ISalesOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrder);
    return this.http
      .post<ISalesOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(salesOrder: ISalesOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesOrder);
    return this.http
      .put<ISalesOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISalesOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISalesOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISalesOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(salesOrder: ISalesOrder): ISalesOrder {
    const copy: ISalesOrder = Object.assign({}, salesOrder, {
      placed: salesOrder.placed && salesOrder.placed.isValid() ? salesOrder.placed.toJSON() : undefined,
      cancelled: salesOrder.cancelled && salesOrder.cancelled.isValid() ? salesOrder.cancelled.toJSON() : undefined,
      shipped: salesOrder.shipped && salesOrder.shipped.isValid() ? salesOrder.shipped.toJSON() : undefined,
      completed: salesOrder.completed && salesOrder.completed.isValid() ? salesOrder.completed.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.placed = res.body.placed ? moment(res.body.placed) : undefined;
      res.body.cancelled = res.body.cancelled ? moment(res.body.cancelled) : undefined;
      res.body.shipped = res.body.shipped ? moment(res.body.shipped) : undefined;
      res.body.completed = res.body.completed ? moment(res.body.completed) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((salesOrder: ISalesOrder) => {
        salesOrder.placed = salesOrder.placed ? moment(salesOrder.placed) : undefined;
        salesOrder.cancelled = salesOrder.cancelled ? moment(salesOrder.cancelled) : undefined;
        salesOrder.shipped = salesOrder.shipped ? moment(salesOrder.shipped) : undefined;
        salesOrder.completed = salesOrder.completed ? moment(salesOrder.completed) : undefined;
      });
    }
    return res;
  }
}
