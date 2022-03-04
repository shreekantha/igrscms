import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHomeImg } from 'app/shared/model/home-img.model';
import { HomeImgService } from './home-img.service';
import { HomeImgDeleteDialogComponent } from './home-img-delete-dialog.component';

@Component({
  selector: 'jhi-home-img',
  templateUrl: './home-img.component.html',
})
export class HomeImgComponent implements OnInit, OnDestroy {
  homeImgs?: IHomeImg[];
  eventSubscriber?: Subscription;

  constructor(
    protected homeImgService: HomeImgService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.homeImgService.query().subscribe((res: HttpResponse<IHomeImg[]>) => (this.homeImgs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHomeImgs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHomeImg): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInHomeImgs(): void {
    this.eventSubscriber = this.eventManager.subscribe('homeImgListModification', () => this.loadAll());
  }

  delete(homeImg: IHomeImg): void {
    const modalRef = this.modalService.open(HomeImgDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.homeImg = homeImg;
  }
}
