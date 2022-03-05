import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { AcademicCalendarComponent } from './academic-calendar.component';
import { AcademicCalendarDetailComponent } from './academic-calendar-detail.component';
import { AcademicCalendarUpdateComponent } from './academic-calendar-update.component';
import { AcademicCalendarDeleteDialogComponent } from './academic-calendar-delete-dialog.component';
import { academicCalendarRoute } from './academic-calendar.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(academicCalendarRoute)],
  declarations: [
    AcademicCalendarComponent,
    AcademicCalendarDetailComponent,
    AcademicCalendarUpdateComponent,
    AcademicCalendarDeleteDialogComponent,
  ],
  entryComponents: [AcademicCalendarDeleteDialogComponent],
})
export class IgrscmsAcademicCalendarModule {}
