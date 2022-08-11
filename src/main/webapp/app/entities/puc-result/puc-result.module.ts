import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { PucResultComponent } from './puc-result.component';
import { PucResultDetailComponent } from './puc-result-detail.component';
import { PucResultUpdateComponent } from './puc-result-update.component';
import { PucResultDeleteDialogComponent } from './puc-result-delete-dialog.component';
import { pucResultRoute } from './puc-result.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(pucResultRoute)],
  declarations: [PucResultComponent, PucResultDetailComponent, PucResultUpdateComponent, PucResultDeleteDialogComponent],
  entryComponents: [PucResultDeleteDialogComponent],
})
export class IgrscmsPucResultModule {}
