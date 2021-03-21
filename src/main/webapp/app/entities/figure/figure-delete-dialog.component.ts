import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFigure } from 'app/shared/model/figure.model';
import { FigureService } from './figure.service';

@Component({
  templateUrl: './figure-delete-dialog.component.html',
})
export class FigureDeleteDialogComponent {
  figure?: IFigure;

  constructor(protected figureService: FigureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.figureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('figureListModification');
      this.activeModal.close();
    });
  }
}
