import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUserApp, UserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
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
    city: [null, [Validators.required]],
    region: [null, [Validators.required]],
    country: [null, [Validators.required]],
    phoneNumber: [null, [Validators.required]],
    cin: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    internalUserId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected userAppService: UserAppService,
    protected userService: UserService,
    protected elementRef: ElementRef,
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
      phoneNumber: userApp.phoneNumber,
      cin: userApp.cin,
      image: userApp.image,
      imageContentType: userApp.imageContentType,
      internalUserId: userApp.internalUserId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('electoralSystemApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
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
