import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { NoticeBoardComponent } from './notice-board.component';
import { NoticeBoardDetailComponent } from './notice-board-detail.component';
import { NoticeBoardUpdateComponent } from './notice-board-update.component';
import { NoticeBoardDeleteDialogComponent } from './notice-board-delete-dialog.component';
import { noticeBoardRoute } from './notice-board.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(noticeBoardRoute)],
  declarations: [NoticeBoardComponent, NoticeBoardDetailComponent, NoticeBoardUpdateComponent, NoticeBoardDeleteDialogComponent],
  entryComponents: [NoticeBoardDeleteDialogComponent],
})
export class IgrscmsNoticeBoardModule {}
