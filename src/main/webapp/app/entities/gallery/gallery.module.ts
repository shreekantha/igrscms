import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { GalleryComponent } from './gallery.component';
import { GalleryDetailComponent } from './gallery-detail.component';
import { GalleryUpdateComponent } from './gallery-update.component';
import { GalleryDeleteDialogComponent } from './gallery-delete-dialog.component';
import { galleryRoute } from './gallery.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(galleryRoute)],
  declarations: [GalleryComponent, GalleryDetailComponent, GalleryUpdateComponent, GalleryDeleteDialogComponent],
  entryComponents: [GalleryDeleteDialogComponent],
})
export class IgrscmsGalleryModule {}
