import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { ClassTimeTableConfigComponent } from './class-time-table-config.component';
import { ClassTimeTableConfigDetailComponent } from './class-time-table-config-detail.component';
import { ClassTimeTableConfigUpdateComponent } from './class-time-table-config-update.component';
import { ClassTimeTableConfigDeleteDialogComponent } from './class-time-table-config-delete-dialog.component';
import { classTimeTableConfigRoute } from './class-time-table-config.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(classTimeTableConfigRoute)],
  declarations: [
    ClassTimeTableConfigComponent,
    ClassTimeTableConfigDetailComponent,
    ClassTimeTableConfigUpdateComponent,
    ClassTimeTableConfigDeleteDialogComponent,
  ],
  entryComponents: [ClassTimeTableConfigDeleteDialogComponent],
})
export class IgrscmsClassTimeTableConfigModule {}
