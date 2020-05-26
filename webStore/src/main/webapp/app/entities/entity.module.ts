import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.WebStoreCustomerModule),
      },
      {
        path: 'sales-order',
        loadChildren: () => import('./orderService/sales-order/sales-order.module').then(m => m.OrderServiceSalesOrderModule),
      },
      {
        path: 'sales-order-item',
        loadChildren: () => import('./orderService/sales-order-item/sales-order-item.module').then(m => m.OrderServiceSalesOrderItemModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./storeService/product/product.module').then(m => m.StoreServiceProductModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./storeService/photo/photo.module').then(m => m.StoreServicePhotoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class WebStoreEntityModule {}
