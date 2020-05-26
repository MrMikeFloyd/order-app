import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebStoreSharedModule } from 'app/shared/shared.module';
import { SalesOrderComponent } from './sales-order.component';
import { SalesOrderDetailComponent } from './sales-order-detail.component';
import { SalesOrderUpdateComponent } from './sales-order-update.component';
import { SalesOrderDeleteDialogComponent } from './sales-order-delete-dialog.component';
import { salesOrderRoute } from './sales-order.route';

@NgModule({
  imports: [WebStoreSharedModule, RouterModule.forChild(salesOrderRoute)],
  declarations: [SalesOrderComponent, SalesOrderDetailComponent, SalesOrderUpdateComponent, SalesOrderDeleteDialogComponent],
  entryComponents: [SalesOrderDeleteDialogComponent],
})
export class OrderServiceSalesOrderModule {}
