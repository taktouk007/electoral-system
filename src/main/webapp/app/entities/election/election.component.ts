import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IElection } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';
import { ElectionDeleteDialogComponent } from './election-delete-dialog.component';

@Component({
  selector: 'jhi-election',
  templateUrl: './election.component.html',
})
export class ElectionComponent implements OnInit, OnDestroy {
  elections?: IElection[];
  eventSubscriber?: Subscription;

  constructor(protected electionService: ElectionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.electionService.query().subscribe((res: HttpResponse<IElection[]>) => (this.elections = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInElections();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IElection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInElections(): void {
    this.eventSubscriber = this.eventManager.subscribe('electionListModification', () => this.loadAll());
  }

  delete(election: IElection): void {
    const modalRef = this.modalService.open(ElectionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.election = election;
  }
}
