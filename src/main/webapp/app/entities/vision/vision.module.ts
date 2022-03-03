import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { VisionComponent } from './vision.component';
import { VisionDetailComponent } from './vision-detail.component';
import { VisionUpdateComponent } from './vision-update.component';
import { VisionDeleteDialogComponent } from './vision-delete-dialog.component';
import { visionRoute } from './vision.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(visionRoute)],
  declarations: [VisionComponent, VisionDetailComponent, VisionUpdateComponent, VisionDeleteDialogComponent],
  entryComponents: [VisionDeleteDialogComponent],
})
export class IgrscmsVisionModule {}
