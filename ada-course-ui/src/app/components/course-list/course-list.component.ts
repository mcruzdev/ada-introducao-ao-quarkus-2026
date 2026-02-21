import { Component, ChangeDetectionStrategy, inject, signal, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CourseListComponent implements OnInit {
  private courseService = inject(CourseService);
  private router = inject(Router);

  courses = this.courseService.allCourses;
  deleteConfirmId = signal<number | null>(null);
  isLoading = signal(false);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loadCourses();
  }

  private loadCourses(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    
    this.courseService.loadCourses().subscribe({
      next: () => {
        this.isLoading.set(false);
      },
      error: (error) => {
        this.isLoading.set(false);
        this.errorMessage.set('Failed to load courses. Please try again.');
        console.error('Error loading courses:', error);
      }
    });
  }

  navigateToCreate(): void {
    this.router.navigate(['/courses/new']);
  }

  navigateToEdit(id: number): void {
    this.router.navigate(['/courses', id, 'edit']);
  }

  navigateToDetail(id: number): void {
    this.router.navigate(['/courses', id]);
  }

  confirmDelete(id: number): void {
    this.deleteConfirmId.set(id);
  }

  cancelDelete(): void {
    this.deleteConfirmId.set(null);
  }

  deleteCourse(id: number): void {
    this.courseService.deleteCourse(id).subscribe({
      next: () => {
        this.deleteConfirmId.set(null);
      },
      error: (error) => {
        this.errorMessage.set('Failed to delete course. Please try again.');
        console.error('Error deleting course:', error);
        this.deleteConfirmId.set(null);
      }
    });
  }

  trackByCourseId(index: number, course: Course): number {
    return course.id;
  }
}
