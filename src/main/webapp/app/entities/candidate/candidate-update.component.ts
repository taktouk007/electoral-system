import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICandidate, Candidate } from 'app/shared/model/candidate.model';
import { CandidateService } from './candidate.service';
import { IElection } from 'app/shared/model/election.model';
import { ElectionService } from 'app/entities/election/election.service';

@Component({
  selector: 'jhi-candidate-update',
  templateUrl: './candidate-update.component.html',
})
export class CandidateUpdateComponent implements OnInit {
  isSaving = false;
  elections: IElection[] = [];

  editForm = this.fb.group({
    id: [],
    electionId: [],
  });

  constructor(
    protected candidateService: CandidateService,
    protected electionService: ElectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidate }) => {
      this.updateForm(candidate);

      this.electionService.query().subscribe((res: HttpResponse<IElection[]>) => (this.elections = res.body || []));
    });
  }

  updateForm(candidate: ICandidate): void {
    this.editForm.patchValue({
      id: candidate.id,
      electionId: candidate.electionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidate = this.createFromForm();
    if (candidate.id !== undefined) {
      this.subscribeToSaveResponse(this.candidateService.update(candidate));
    } else {
      this.subscribeToSaveResponse(this.candidateService.create(candidate));
    }
  }

  private createFromForm(): ICandidate {
    return {
      ...new Candidate(),
      id: this.editForm.get(['id'])!.value,
      electionId: this.editForm.get(['electionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidate>>): void {
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
