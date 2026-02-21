import { Routes } from '@angular/router';
import { CourseListComponent } from './components/course-list/course-list.component';
import { CourseFormComponent } from './components/course-form/course-form.component';
import { CourseDetailComponent } from './components/course-detail/course-detail.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/courses',
    pathMatch: 'full'
  },
  {
    path: 'courses',
    component: CourseListComponent
  },
  {
    path: 'courses/new',
    component: CourseFormComponent
  },
  {
    path: 'courses/:id',
    component: CourseDetailComponent
  },
  {
    path: 'courses/:id/edit',
    component: CourseFormComponent
  },
  {
    path: '**',
    redirectTo: '/courses'
  }
];
