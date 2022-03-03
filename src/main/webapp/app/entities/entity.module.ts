import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'institute',
        loadChildren: () => import('./institute/institute.module').then(m => m.IgrscmsInstituteModule),
      },
      {
        path: 'contact-details',
        loadChildren: () => import('./contact-details/contact-details.module').then(m => m.IgrscmsContactDetailsModule),
      },
      {
        path: 'news',
        loadChildren: () => import('./news/news.module').then(m => m.IgrscmsNewsModule),
      },
      {
        path: 'term',
        loadChildren: () => import('./term/term.module').then(m => m.IgrscmsTermModule),
      },
      {
        path: 'gallery-cat',
        loadChildren: () => import('./gallery-cat/gallery-cat.module').then(m => m.IgrscmsGalleryCatModule),
      },
      {
        path: 'gallery',
        loadChildren: () => import('./gallery/gallery.module').then(m => m.IgrscmsGalleryModule),
      },
      {
        path: 'about-us',
        loadChildren: () => import('./about-us/about-us.module').then(m => m.IgrscmsAboutUsModule),
      },
      {
        path: 'speaker-desk',
        loadChildren: () => import('./speaker-desk/speaker-desk.module').then(m => m.IgrscmsSpeakerDeskModule),
      },
      {
        path: 'vision',
        loadChildren: () => import('./vision/vision.module').then(m => m.IgrscmsVisionModule),
      },
      {
        path: 'mission',
        loadChildren: () => import('./mission/mission.module').then(m => m.IgrscmsMissionModule),
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.IgrscmsProfileModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class IgrscmsEntityModule {}
