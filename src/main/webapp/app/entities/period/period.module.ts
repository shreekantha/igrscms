import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { PeriodComponent } from './period.component';
import { PeriodDetailComponent } from './period-detail.component';
import { PeriodUpdateComponent } from './period-update.component';
import { PeriodDeleteDialogComponent } from './period-delete-dialog.component';
import { periodRoute } from './period.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(periodRoute)],
  declarations: [PeriodComponent, PeriodDetailComponent, PeriodUpdateComponent, PeriodDeleteDialogComponent],
  entryComponents: [PeriodDeleteDialogComponent],
})
export class IgrscmsPeriodModule {}
