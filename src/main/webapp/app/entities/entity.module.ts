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
      {
        path: 'notice-board',
        loadChildren: () => import('./notice-board/notice-board.module').then(m => m.IgrscmsNoticeBoardModule),
      },
      {
        path: 'home-img',
        loadChildren: () => import('./home-img/home-img.module').then(m => m.IgrscmsHomeImgModule),
      },
      {
        path: 'degree',
        loadChildren: () => import('./degree/degree.module').then(m => m.IgrscmsDegreeModule),
      },
      {
        path: 'scheme',
        loadChildren: () => import('./scheme/scheme.module').then(m => m.IgrscmsSchemeModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.IgrscmsDepartmentModule),
      },
      {
        path: 'academic-calendar',
        loadChildren: () => import('./academic-calendar/academic-calendar.module').then(m => m.IgrscmsAcademicCalendarModule),
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.IgrscmsCourseModule),
      },
      {
        path: 'period',
        loadChildren: () => import('./period/period.module').then(m => m.IgrscmsPeriodModule),
      },
      {
        path: 'class-time-table-config',
        loadChildren: () =>
          import('./class-time-table-config/class-time-table-config.module').then(m => m.IgrscmsClassTimeTableConfigModule),
      },
      {
        path: 'class-time-table',
        loadChildren: () => import('./class-time-table/class-time-table.module').then(m => m.IgrscmsClassTimeTableModule),
      },
      {
        path: 'exam-time-table',
        loadChildren: () => import('./exam-time-table/exam-time-table.module').then(m => m.IgrscmsExamTimeTableModule),
      },
      {
        path: 'user-profile',
        loadChildren: () => import('./user-profile/user-profile.module').then(m => m.IgrscmsUserProfileModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class IgrscmsEntityModule {}
