import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { GalleryCatComponent } from './gallery-cat.component';
import { GalleryCatDetailComponent } from './gallery-cat-detail.component';
import { GalleryCatUpdateComponent } from './gallery-cat-update.component';
import { GalleryCatDeleteDialogComponent } from './gallery-cat-delete-dialog.component';
import { galleryCatRoute } from './gallery-cat.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(galleryCatRoute)],
  declarations: [GalleryCatComponent, GalleryCatDetailComponent, GalleryCatUpdateComponent, GalleryCatDeleteDialogComponent],
  entryComponents: [GalleryCatDeleteDialogComponent],
})
export class IgrscmsGalleryCatModule {}
