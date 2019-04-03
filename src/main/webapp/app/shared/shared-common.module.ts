import { NgModule } from '@angular/core';

import { EserviceSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [EserviceSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [EserviceSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EserviceSharedCommonModule {}
