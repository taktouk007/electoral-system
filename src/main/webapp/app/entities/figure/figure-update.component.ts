import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFigure, Figure } from 'app/shared/model/figure.model';
import { FigureService } from './figure.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate/candidate.service';

@Component({
  selector: 'jhi-figure-update',
  templateUrl: './figure-update.component.html',
})
export class FigureUpdateComponent implements OnInit {
  isSaving = false;
  candidates: ICandidate[] = [];

  editForm = this.fb.group({
    id: [],
    fullName: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    linkedIn: [],
    facebook: [],
    twitter: [],
    candidateId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected figureService: FigureService,
    protected candidateService: CandidateService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ figure }) => {
      this.updateForm(figure);

      this.candidateService.query().subscribe((res: HttpResponse<ICandidate[]>) => (this.candidates = res.body || []));
    });
  }

  updateForm(figure: IFigure): void {
    this.editForm.patchValue({
      id: figure.id,
      fullName: figure.fullName,
      image: figure.image,
      imageContentType: figure.imageContentType,
      linkedIn: figure.linkedIn,
      facebook: figure.facebook,
      twitter: figure.twitter,
      candidateId: figure.candidateId,
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
    const figure = this.createFromForm();
    if (figure.id !== undefined) {
      this.subscribeToSaveResponse(this.figureService.update(figure));
    } else {
      this.subscribeToSaveResponse(this.figureService.create(figure));
    }
  }

  private createFromForm(): IFigure {
    return {
      ...new Figure(),
      id: this.editForm.get(['id'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      linkedIn: this.editForm.get(['linkedIn'])!.value,
      facebook: this.editForm.get(['facebook'])!.value,
      twitter: this.editForm.get(['twitter'])!.value,
      candidateId: this.editForm.get(['candidateId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFigure>>): void {
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

  trackById(index: number, item: ICandidate): any {
    return item.id;
  }
}
