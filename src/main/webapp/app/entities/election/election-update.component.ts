import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IElection, Election } from 'app/shared/model/election.model';
import { ElectionService } from './election.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';

@Component({
  selector: 'jhi-election-update',
  templateUrl: './election-update.component.html',
})
export class ElectionUpdateComponent implements OnInit {
  isSaving = false;
  userapps: IUserApp[] = [];

  editForm = this.fb.group({
    id: [],
    targetFunction: [null, [Validators.required]],
    startDate: [],
    endDate: [],
    scope: [],
    city: [],
    region: [],
    country: [],
    userAppId: [],
  });

  constructor(
    protected electionService: ElectionService,
    protected userAppService: UserAppService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ election }) => {
      if (!election.id) {
        const today = moment().startOf('day');
        election.startDate = today;
        election.endDate = today;
      }

      this.updateForm(election);

      this.userAppService.query().subscribe((res: HttpResponse<IUserApp[]>) => (this.userapps = res.body || []));
    });
  }

  updateForm(election: IElection): void {
    this.editForm.patchValue({
      id: election.id,
      targetFunction: election.targetFunction,
      startDate: election.startDate ? election.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: election.endDate ? election.endDate.format(DATE_TIME_FORMAT) : null,
      scope: election.scope,
      city: election.city,
      region: election.region,
      country: election.country,
      userAppId: election.userAppId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const election = this.createFromForm();
    if (election.id !== undefined) {
      this.subscribeToSaveResponse(this.electionService.update(election));
    } else {
      this.subscribeToSaveResponse(this.electionService.create(election));
    }
  }

  private createFromForm(): IElection {
    return {
      ...new Election(),
      id: this.editForm.get(['id'])!.value,
      targetFunction: this.editForm.get(['targetFunction'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      scope: this.editForm.get(['scope'])!.value,
      city: this.editForm.get(['city'])!.value,
      region: this.editForm.get(['region'])!.value,
      country: this.editForm.get(['country'])!.value,
      userAppId: this.editForm.get(['userAppId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElection>>): void {
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

  trackById(index: number, item: IUserApp): any {
    return item.id;
  }
}
