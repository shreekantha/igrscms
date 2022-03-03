import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { SpeakerDeskComponent } from './speaker-desk.component';
import { SpeakerDeskDetailComponent } from './speaker-desk-detail.component';
import { SpeakerDeskUpdateComponent } from './speaker-desk-update.component';
import { SpeakerDeskDeleteDialogComponent } from './speaker-desk-delete-dialog.component';
import { speakerDeskRoute } from './speaker-desk.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(speakerDeskRoute)],
  declarations: [SpeakerDeskComponent, SpeakerDeskDetailComponent, SpeakerDeskUpdateComponent, SpeakerDeskDeleteDialogComponent],
  entryComponents: [SpeakerDeskDeleteDialogComponent],
})
export class IgrscmsSpeakerDeskModule {}
