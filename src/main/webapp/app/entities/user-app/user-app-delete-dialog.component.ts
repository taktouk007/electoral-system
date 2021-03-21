import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';

@Component({
  templateUrl: './user-app-delete-dialog.component.html',
})
export class UserAppDeleteDialogComponent {
  userApp?: IUserApp;

  constructor(protected userAppService: UserAppService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userAppService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userAppListModification');
      this.activeModal.close();
    });
  }
}
