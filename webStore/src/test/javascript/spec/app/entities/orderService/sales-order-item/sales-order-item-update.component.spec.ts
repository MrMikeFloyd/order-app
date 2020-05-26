import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WebStoreTestModule } from '../../../../test.module';
import { SalesOrderItemUpdateComponent } from 'app/entities/orderService/sales-order-item/sales-order-item-update.component';
import { SalesOrderItemService } from 'app/entities/orderService/sales-order-item/sales-order-item.service';
import { SalesOrderItem } from 'app/shared/model/orderService/sales-order-item.model';

describe('Component Tests', () => {
  describe('SalesOrderItem Management Update Component', () => {
    let comp: SalesOrderItemUpdateComponent;
    let fixture: ComponentFixture<SalesOrderItemUpdateComponent>;
    let service: SalesOrderItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SalesOrderItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesOrderItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesOrderItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SalesOrderItem(123);
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
        const entity = new SalesOrderItem();
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
