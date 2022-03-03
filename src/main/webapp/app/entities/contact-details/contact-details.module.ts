import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { ContactDetailsComponent } from './contact-details.component';
import { ContactDetailsDetailComponent } from './contact-details-detail.component';
import { ContactDetailsUpdateComponent } from './contact-details-update.component';
import { ContactDetailsDeleteDialogComponent } from './contact-details-delete-dialog.component';
import { contactDetailsRoute } from './contact-details.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(contactDetailsRoute)],
  declarations: [
    ContactDetailsComponent,
    ContactDetailsDetailComponent,
    ContactDetailsUpdateComponent,
    ContactDetailsDeleteDialogComponent,
  ],
  entryComponents: [ContactDetailsDeleteDialogComponent],
})
export class IgrscmsContactDetailsModule {}
