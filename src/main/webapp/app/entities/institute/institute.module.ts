import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { InstituteComponent } from './institute.component';
import { InstituteDetailComponent } from './institute-detail.component';
import { InstituteUpdateComponent } from './institute-update.component';
import { InstituteDeleteDialogComponent } from './institute-delete-dialog.component';
import { instituteRoute } from './institute.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(instituteRoute)],
  declarations: [InstituteComponent, InstituteDetailComponent, InstituteUpdateComponent, InstituteDeleteDialogComponent],
  entryComponents: [InstituteDeleteDialogComponent],
})
export class IgrscmsInstituteModule {}
