import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVote, Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { IElection } from 'app/shared/model/election.model';
import { ElectionService } from 'app/entities/election/election.service';

@Component({
  selector: 'jhi-vote-update',
  templateUrl: './vote-update.component.html',
})
export class VoteUpdateComponent implements OnInit {
  isSaving = false;
  elections: IElection[] = [];

  editForm = this.fb.group({
    id: [],
    candidateId: [],
    submissionDate: [],
    electionId: [],
  });

  constructor(
    protected voteService: VoteService,
    protected electionService: ElectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vote }) => {
      if (!vote.id) {
        const today = moment().startOf('day');
        vote.submissionDate = today;
      }

      this.updateForm(vote);

      this.electionService.query().subscribe((res: HttpResponse<IElection[]>) => (this.elections = res.body || []));
    });
  }

  updateForm(vote: IVote): void {
    this.editForm.patchValue({
      id: vote.id,
      candidateId: vote.candidateId,
      submissionDate: vote.submissionDate ? vote.submissionDate.format(DATE_TIME_FORMAT) : null,
      electionId: vote.electionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vote = this.createFromForm();
    if (vote.id !== undefined) {
      this.subscribeToSaveResponse(this.voteService.update(vote));
    } else {
      this.subscribeToSaveResponse(this.voteService.create(vote));
    }
  }

  private createFromForm(): IVote {
    return {
      ...new Vote(),
      id: this.editForm.get(['id'])!.value,
      candidateId: this.editForm.get(['candidateId'])!.value,
      submissionDate: this.editForm.get(['submissionDate'])!.value
        ? moment(this.editForm.get(['submissionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      electionId: this.editForm.get(['electionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVote>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IElection): any {
    return item.id;
  }
}
