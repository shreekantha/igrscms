import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { HomeImgComponent } from './home-img.component';
import { HomeImgDetailComponent } from './home-img-detail.component';
import { HomeImgUpdateComponent } from './home-img-update.component';
import { HomeImgDeleteDialogComponent } from './home-img-delete-dialog.component';
import { homeImgRoute } from './home-img.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(homeImgRoute)],
  declarations: [HomeImgComponent, HomeImgDetailComponent, HomeImgUpdateComponent, HomeImgDeleteDialogComponent],
  entryComponents: [HomeImgDeleteDialogComponent],
})
export class IgrscmsHomeImgModule {}
