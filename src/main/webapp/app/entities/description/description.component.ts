import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDescription } from 'app/shared/model/description.model';
import { AccountService } from 'app/core';
import { DescriptionService } from './description.service';

@Component({
    selector: 'jhi-description',
    templateUrl: './description.component.html'
})
export class DescriptionComponent implements OnInit, OnDestroy {
    descriptions: IDescription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected descriptionService: DescriptionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.descriptionService
            .query()
            .pipe(
                filter((res: HttpResponse<IDescription[]>) => res.ok),
                map((res: HttpResponse<IDescription[]>) => res.body)
            )
            .subscribe(
                (res: IDescription[]) => {
                    this.descriptions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDescriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDescription) {
        return item.id;
    }

    registerChangeInDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('descriptionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
