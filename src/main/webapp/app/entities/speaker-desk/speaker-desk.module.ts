import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { CKEditorModule } from 'ckeditor4-angular';
import { SpeakerDeskDeleteDialogComponent } from './speaker-desk-delete-dialog.component';
import { SpeakerDeskDetailComponent } from './speaker-desk-detail.component';
import { SpeakerDeskUpdateComponent } from './speaker-desk-update.component';
import { SpeakerDeskComponent } from './speaker-desk.component';
import { speakerDeskRoute } from './speaker-desk.route';

@NgModule({
  imports: [IgrscmsSharedModule, CKEditorModule, RouterModule.forChild(speakerDeskRoute)],
  declarations: [SpeakerDeskComponent, SpeakerDeskDetailComponent, SpeakerDeskUpdateComponent, SpeakerDeskDeleteDialogComponent],
  entryComponents: [SpeakerDeskDeleteDialogComponent],
})
export class IgrscmsSpeakerDeskModule {}
