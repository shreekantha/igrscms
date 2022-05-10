import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { StudentProfileComponent } from './student-profile.component';
import { StudentProfileDetailComponent } from './student-profile-detail.component';
import { StudentProfileUpdateComponent } from './student-profile-update.component';
import { StudentProfileDeleteDialogComponent } from './student-profile-delete-dialog.component';
import { studentProfileRoute } from './student-profile.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(studentProfileRoute)],
  declarations: [
    StudentProfileComponent,
    StudentProfileDetailComponent,
    StudentProfileUpdateComponent,
    StudentProfileDeleteDialogComponent,
  ],
  entryComponents: [StudentProfileDeleteDialogComponent],
})
export class IgrscmsStudentProfileModule {}
