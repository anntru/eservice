import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EserviceSharedModule } from 'app/shared';
import {
    DescriptionComponent,
    DescriptionDetailComponent,
    DescriptionUpdateComponent,
    DescriptionDeletePopupComponent,
    DescriptionDeleteDialogComponent,
    descriptionRoute,
    descriptionPopupRoute
} from './';

const ENTITY_STATES = [...descriptionRoute, ...descriptionPopupRoute];

@NgModule({
    imports: [EserviceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DescriptionComponent,
        DescriptionDetailComponent,
        DescriptionUpdateComponent,
        DescriptionDeleteDialogComponent,
        DescriptionDeletePopupComponent
    ],
    entryComponents: [DescriptionComponent, DescriptionUpdateComponent, DescriptionDeleteDialogComponent, DescriptionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EserviceDescriptionModule {}
