import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISalesOrderItem, SalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';
import { SalesOrderItemService } from './sales-order-item.service';
import { SalesOrderItemComponent } from './sales-order-item.component';
import { SalesOrderItemDetailComponent } from './sales-order-item-detail.component';
import { SalesOrderItemUpdateComponent } from './sales-order-item-update.component';

@Injectable({ providedIn: 'root' })
export class SalesOrderItemResolve implements Resolve<ISalesOrderItem> {
  constructor(private service: SalesOrderItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalesOrderItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((salesOrderItem: HttpResponse<SalesOrderItem>) => {
          if (salesOrderItem.body) {
            return of(salesOrderItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalesOrderItem());
  }
}

export const salesOrderItemRoute: Routes = [
  {
    path: '',
    component: SalesOrderItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrderItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalesOrderItemDetailComponent,
    resolve: {
      salesOrderItem: SalesOrderItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrderItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalesOrderItemUpdateComponent,
    resolve: {
      salesOrderItem: SalesOrderItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrderItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalesOrderItemUpdateComponent,
    resolve: {
      salesOrderItem: SalesOrderItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'webStoreApp.orderServiceSalesOrderItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
