import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICulture, Culture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-culture-update',
  templateUrl: './culture-update.component.html',
})
export class CultureUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    era: [],
    kind: [],
    image: [],
    imageContentType: [],
    name: [],
    unit: [],
    unitDescription: [],
    quarter: [],
    quarterDescription: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected cultureService: CultureService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ culture }) => {
      this.updateForm(culture);
    });
  }

  updateForm(culture: ICulture): void {
    this.editForm.patchValue({
      id: culture.id,
      era: culture.era,
      kind: culture.kind,
      image: culture.image,
      imageContentType: culture.imageContentType,
      name: culture.name,
      unit: culture.unit,
      unitDescription: culture.unitDescription,
      quarter: culture.quarter,
      quarterDescription: culture.quarterDescription,
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
        new JhiEventWithContent<AlertError>('fanpageApp.error', { message: err.message })
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
    const culture = this.createFromForm();
    if (culture.id !== undefined) {
      this.subscribeToSaveResponse(this.cultureService.update(culture));
    } else {
      this.subscribeToSaveResponse(this.cultureService.create(culture));
    }
  }

  private createFromForm(): ICulture {
    return {
      ...new Culture(),
      id: this.editForm.get(['id'])!.value,
      era: this.editForm.get(['era'])!.value,
      kind: this.editForm.get(['kind'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      name: this.editForm.get(['name'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      unitDescription: this.editForm.get(['unitDescription'])!.value,
      quarter: this.editForm.get(['quarter'])!.value,
      quarterDescription: this.editForm.get(['quarterDescription'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICulture>>): void {
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
}
