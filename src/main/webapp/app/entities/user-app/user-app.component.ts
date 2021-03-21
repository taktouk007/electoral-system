import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';
import { UserAppDeleteDialogComponent } from './user-app-delete-dialog.component';

@Component({
  selector: 'jhi-user-app',
  templateUrl: './user-app.component.html',
})
export class UserAppComponent implements OnInit, OnDestroy {
  userApps?: IUserApp[];
  eventSubscriber?: Subscription;

  constructor(protected userAppService: UserAppService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.userAppService.query().subscribe((res: HttpResponse<IUserApp[]>) => (this.userApps = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserApps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserApp): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserApps(): void {
    this.eventSubscriber = this.eventManager.subscribe('userAppListModification', () => this.loadAll());
  }

  delete(userApp: IUserApp): void {
    const modalRef = this.modalService.open(UserAppDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userApp = userApp;
  }
}
