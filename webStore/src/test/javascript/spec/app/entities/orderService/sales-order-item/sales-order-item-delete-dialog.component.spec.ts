import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WebStoreTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { SalesOrderItemDeleteDialogComponent } from 'app/entities/orderService/sales-order-item/sales-order-item-delete-dialog.component';
import { SalesOrderItemService } from 'app/entities/orderService/sales-order-item/sales-order-item.service';

describe('Component Tests', () => {
  describe('SalesOrderItem Management Delete Component', () => {
    let comp: SalesOrderItemDeleteDialogComponent;
    let fixture: ComponentFixture<SalesOrderItemDeleteDialogComponent>;
    let service: SalesOrderItemService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebStoreTestModule],
        declarations: [SalesOrderItemDeleteDialogComponent],
      })
        .overrideTemplate(SalesOrderItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalesOrderItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesOrderItemService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
