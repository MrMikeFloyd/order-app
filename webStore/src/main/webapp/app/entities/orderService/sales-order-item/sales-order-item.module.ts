import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebStoreSharedModule } from 'app/shared/shared.module';
import { SalesOrderItemComponent } from './sales-order-item.component';
import { SalesOrderItemDetailComponent } from './sales-order-item-detail.component';
import { SalesOrderItemUpdateComponent } from './sales-order-item-update.component';
import { SalesOrderItemDeleteDialogComponent } from './sales-order-item-delete-dialog.component';
import { salesOrderItemRoute } from './sales-order-item.route';

@NgModule({
  imports: [WebStoreSharedModule, RouterModule.forChild(salesOrderItemRoute)],
  declarations: [
    SalesOrderItemComponent,
    SalesOrderItemDetailComponent,
    SalesOrderItemUpdateComponent,
    SalesOrderItemDeleteDialogComponent,
  ],
  entryComponents: [SalesOrderItemDeleteDialogComponent],
})
export class OrderServiceSalesOrderItemModule {}
