import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { ClassTimeTableComponent } from './class-time-table.component';
import { ClassTimeTableDetailComponent } from './class-time-table-detail.component';
import { ClassTimeTableUpdateComponent } from './class-time-table-update.component';
import { ClassTimeTableDeleteDialogComponent } from './class-time-table-delete-dialog.component';
import { classTimeTableRoute } from './class-time-table.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(classTimeTableRoute)],
  declarations: [
    ClassTimeTableComponent,
    ClassTimeTableDetailComponent,
    ClassTimeTableUpdateComponent,
    ClassTimeTableDeleteDialogComponent,
  ],
  entryComponents: [ClassTimeTableDeleteDialogComponent],
})
export class IgrscmsClassTimeTableModule {}
