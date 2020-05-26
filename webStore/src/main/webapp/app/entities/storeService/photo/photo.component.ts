import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhoto } from 'app/shared/model/storeService/photo.model';
import { PhotoService } from './photo.service';
import { PhotoDeleteDialogComponent } from './photo-delete-dialog.component';

@Component({
  selector: 'jhi-photo',
  templateUrl: './photo.component.html',
})
export class PhotoComponent implements OnInit, OnDestroy {
  photos?: IPhoto[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected photoService: PhotoService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.photoService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPhoto[]>) => (this.photos = res.body || []));
      return;
    }

    this.photoService.query().subscribe((res: HttpResponse<IPhoto[]>) => (this.photos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhotos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhoto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPhotos(): void {
    this.eventSubscriber = this.eventManager.subscribe('photoListModification', () => this.loadAll());
  }

  delete(photo: IPhoto): void {
    const modalRef = this.modalService.open(PhotoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.photo = photo;
  }
}
