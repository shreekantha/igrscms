import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { AboutUsComponent } from './about-us.component';
import { AboutUsDetailComponent } from './about-us-detail.component';
import { AboutUsUpdateComponent } from './about-us-update.component';
import { AboutUsDeleteDialogComponent } from './about-us-delete-dialog.component';
import { aboutUsRoute } from './about-us.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(aboutUsRoute)],
  declarations: [AboutUsComponent, AboutUsDetailComponent, AboutUsUpdateComponent, AboutUsDeleteDialogComponent],
  entryComponents: [AboutUsDeleteDialogComponent],
})
export class IgrscmsAboutUsModule {}
