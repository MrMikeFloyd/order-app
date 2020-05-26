import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISalesOrder, SalesOrder } from 'app/shared/model/orderService/sales-order.model';
import { SalesOrderService } from './sales-order.service';
import { SalesOrderComponent } from './sales-order.component';
import { SalesOrderDetailComponent } from './sales-order-detail.component';
import { SalesOrderUpdateComponent } from './sales-order-update.component';

@Injectable({ providedIn: 'root' })
export class SalesOrderResolve implements Resolve<ISalesOrder> {
  constructor(private service: SalesOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalesOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((salesOrder: HttpResponse<SalesOrder>) => {
          if (salesOrder.body) {
            return of(salesOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalesOrder());
  }
}

export const salesOrderRoute: Routes = [
  {
    path: '',
    component: SalesOrderComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalesOrderDetailComponent,
    resolve: {
      salesOrder: SalesOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalesOrderUpdateComponent,
    resolve: {
      salesOrder: SalesOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalesOrderUpdateComponent,
    resolve: {
      salesOrder: SalesOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
