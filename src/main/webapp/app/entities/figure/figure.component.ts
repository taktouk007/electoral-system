import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFigure } from 'app/shared/model/figure.model';
import { FigureService } from './figure.service';
import { FigureDeleteDialogComponent } from './figure-delete-dialog.component';

@Component({
  selector: 'jhi-figure',
  templateUrl: './figure.component.html',
})
export class FigureComponent implements OnInit, OnDestroy {
  figures?: IFigure[];
  eventSubscriber?: Subscription;

  constructor(
    protected figureService: FigureService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.figureService.query().subscribe((res: HttpResponse<IFigure[]>) => (this.figures = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFigures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFigure): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInFigures(): void {
    this.eventSubscriber = this.eventManager.subscribe('figureListModification', () => this.loadAll());
  }

  delete(figure: IFigure): void {
    const modalRef = this.modalService.open(FigureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.figure = figure;
  }
}
