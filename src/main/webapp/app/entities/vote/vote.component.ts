import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { VoteDeleteDialogComponent } from './vote-delete-dialog.component';

@Component({
  selector: 'jhi-vote',
  templateUrl: './vote.component.html',
})
export class VoteComponent implements OnInit, OnDestroy {
  votes?: IVote[];
  eventSubscriber?: Subscription;

  constructor(protected voteService: VoteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.voteService.query().subscribe((res: HttpResponse<IVote[]>) => (this.votes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVotes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVote): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVotes(): void {
    this.eventSubscriber = this.eventManager.subscribe('voteListModification', () => this.loadAll());
  }

  delete(vote: IVote): void {
    const modalRef = this.modalService.open(VoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vote = vote;
  }
}
