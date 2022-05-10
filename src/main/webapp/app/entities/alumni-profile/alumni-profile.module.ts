import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { AlumniProfileComponent } from './alumni-profile.component';
import { AlumniProfileDetailComponent } from './alumni-profile-detail.component';
import { AlumniProfileUpdateComponent } from './alumni-profile-update.component';
import { AlumniProfileDeleteDialogComponent } from './alumni-profile-delete-dialog.component';
import { alumniProfileRoute } from './alumni-profile.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(alumniProfileRoute)],
  declarations: [AlumniProfileComponent, AlumniProfileDetailComponent, AlumniProfileUpdateComponent, AlumniProfileDeleteDialogComponent],
  entryComponents: [AlumniProfileDeleteDialogComponent],
})
export class IgrscmsAlumniProfileModule {}
