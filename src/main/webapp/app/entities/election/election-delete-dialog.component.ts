import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElection } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';

@Component({
  templateUrl: './election-delete-dialog.component.html',
})
export class ElectionDeleteDialogComponent {
  election?: IElection;

  constructor(protected electionService: ElectionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.electionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('electionListModification');
      this.activeModal.close();
    });
  }
}
