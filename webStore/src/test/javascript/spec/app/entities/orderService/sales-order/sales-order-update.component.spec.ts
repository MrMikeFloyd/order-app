import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WebStoreTestModule } from '../../../../test.module';
import { SalesOrderUpdateComponent } from 'app/entities/orderService/sales-order/sales-order-update.component';
import { SalesOrderService } from 'app/entities/orderService/sales-order/sales-order.service';
import { SalesOrder } from 'app/shared/model/orderService/sales-order.model';

describe('Component Tests', () => {
  describe('SalesOrder Management Update Component', () => {
    let comp: SalesOrderUpdateComponent;
    let fixture: ComponentFixture<SalesOrderUpdateComponent>;
    let service: SalesOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SalesOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SalesOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SalesOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
