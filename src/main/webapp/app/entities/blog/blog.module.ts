import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IgrscmsSharedModule } from 'app/shared/shared.module';
import { CKEditorModule } from 'ckeditor4-angular';
import { BlogDeleteDialogComponent } from './blog-delete-dialog.component';
import { BlogDetailComponent } from './blog-detail.component';
import { BlogUpdateComponent } from './blog-update.component';
import { BlogComponent } from './blog.component';
import { blogRoute } from './blog.route';

@NgModule({
  imports: [IgrscmsSharedModule, RouterModule.forChild(blogRoute), CKEditorModule],
  declarations: [BlogComponent, BlogDetailComponent, BlogUpdateComponent, BlogDeleteDialogComponent],
  entryComponents: [BlogDeleteDialogComponent],
})
export class IgrscmsBlogModule {}
