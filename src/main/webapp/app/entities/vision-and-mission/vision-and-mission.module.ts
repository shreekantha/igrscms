import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { CKEditorModule } from 'ckeditor4-angular';
import { VisionAndMissionDeleteDialogComponent } from './vision-and-mission-delete-dialog.component';
import { VisionAndMissionDetailComponent } from './vision-and-mission-detail.component';
import { VisionAndMissionUpdateComponent } from './vision-and-mission-update.component';
import { VisionAndMissionComponent } from './vision-and-mission.component';
import { visionAndMissionRoute } from './vision-and-mission.route';

@NgModule({
  imports: [IgrscmsSharedModule, CKEditorModule, RouterModule.forChild(visionAndMissionRoute)],
  declarations: [
    VisionAndMissionComponent,
    VisionAndMissionDetailComponent,
    VisionAndMissionUpdateComponent,
    VisionAndMissionDeleteDialogComponent,
  ],
  entryComponents: [VisionAndMissionDeleteDialogComponent],
})
export class IgrscmsVisionAndMissionModule {}
