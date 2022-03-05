import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { DegreeComponent } from './degree.component';
import { DegreeDetailComponent } from './degree-detail.component';
import { DegreeUpdateComponent } from './degree-update.component';
import { DegreeDeleteDialogComponent } from './degree-delete-dialog.component';
import { degreeRoute } from './degree.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(degreeRoute)],
  declarations: [DegreeComponent, DegreeDetailComponent, DegreeUpdateComponent, DegreeDeleteDialogComponent],
  entryComponents: [DegreeDeleteDialogComponent],
})
export class IgrscmsDegreeModule {}
