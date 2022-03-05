import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { ExamTimeTableComponent } from './exam-time-table.component';
import { ExamTimeTableDetailComponent } from './exam-time-table-detail.component';
import { ExamTimeTableUpdateComponent } from './exam-time-table-update.component';
import { ExamTimeTableDeleteDialogComponent } from './exam-time-table-delete-dialog.component';
import { examTimeTableRoute } from './exam-time-table.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(examTimeTableRoute)],
  declarations: [ExamTimeTableComponent, ExamTimeTableDetailComponent, ExamTimeTableUpdateComponent, ExamTimeTableDeleteDialogComponent],
  entryComponents: [ExamTimeTableDeleteDialogComponent],
})
export class IgrscmsExamTimeTableModule {}
