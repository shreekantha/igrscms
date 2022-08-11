import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { SslcResultComponent } from './sslc-result.component';
import { SslcResultDetailComponent } from './sslc-result-detail.component';
import { SslcResultUpdateComponent } from './sslc-result-update.component';
import { SslcResultDeleteDialogComponent } from './sslc-result-delete-dialog.component';
import { sslcResultRoute } from './sslc-result.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(sslcResultRoute)],
  declarations: [SslcResultComponent, SslcResultDetailComponent, SslcResultUpdateComponent, SslcResultDeleteDialogComponent],
  entryComponents: [SslcResultDeleteDialogComponent],
})
export class IgrscmsSslcResultModule {}
