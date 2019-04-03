import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDescription } from 'app/shared/model/description.model';
import { DescriptionService } from './description.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-description-update',
    templateUrl: './description-update.component.html'
})
export class DescriptionUpdateComponent implements OnInit {
    description: IDescription;
    isSaving: boolean;

    items: IItem[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected descriptionService: DescriptionService,
        protected itemService: ItemService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ description }) => {
            this.description = description;
        });
        this.itemService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItem[]>) => response.body)
            )
            .subscribe((res: IItem[]) => (this.items = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.description.id !== undefined) {
            this.subscribeToSaveResponse(this.descriptionService.update(this.description));
        } else {
            this.subscribeToSaveResponse(this.descriptionService.create(this.description));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDescription>>) {
        result.subscribe((res: HttpResponse<IDescription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackItemById(index: number, item: IItem) {
        return item.id;
    }
}
