import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserApp, UserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-app-update',
  templateUrl: './user-app-update.component.html',
})
export class UserAppUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    city: [],
    region: [],
    country: [],
    internalUserId: [],
  });

  constructor(
    protected userAppService: UserAppService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userApp }) => {
      this.updateForm(userApp);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userApp: IUserApp): void {
    this.editForm.patchValue({
      id: userApp.id,
      city: userApp.city,
      region: userApp.region,
      country: userApp.country,
      internalUserId: userApp.internalUserId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userApp = this.createFromForm();
    if (userApp.id !== undefined) {
      this.subscribeToSaveResponse(this.userAppService.update(userApp));
    } else {
      this.subscribeToSaveResponse(this.userAppService.create(userApp));
    }
  }

  private createFromForm(): IUserApp {
    return {
      ...new UserApp(),
      id: this.editForm.get(['id'])!.value,
      city: this.editForm.get(['city'])!.value,
      region: this.editForm.get(['region'])!.value,
      country: this.editForm.get(['country'])!.value,
      internalUserId: this.editForm.get(['internalUserId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserApp>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
