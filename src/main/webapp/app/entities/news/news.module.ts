import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { CKEditorModule } from 'ckeditor4-angular';
import { NewsDeleteDialogComponent } from './news-delete-dialog.component';
import { NewsDetailComponent } from './news-detail.component';
import { NewsUpdateComponent } from './news-update.component';
import { NewsComponent } from './news.component';
import { newsRoute } from './news.route';

@NgModule({
  imports: [IgrscmsSharedModule, CKEditorModule, RouterModule.forChild(newsRoute)],
  declarations: [NewsComponent, NewsDetailComponent, NewsUpdateComponent, NewsDeleteDialogComponent],
  entryComponents: [NewsDeleteDialogComponent],
})
export class IgrscmsNewsModule {}
