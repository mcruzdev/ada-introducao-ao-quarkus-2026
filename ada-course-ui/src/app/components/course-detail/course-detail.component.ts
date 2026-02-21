import { Component, ChangeDetectionStrategy, inject, signal, computed, effect } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';
import { Lesson } from '../../models/lesson.model';

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrl: './course-detail.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CourseDetailComponent {
  private courseService = inject(CourseService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  courseId = signal<number | null>(null);
  course = signal<Course | null>(null);
  deleteLessonConfirmId = signal<number | null>(null);
  showLessonForm = signal(false);
  lessonName = signal('');
  lessonError = signal<string | null>(null);
  isLoading = signal(false);
  errorMessage = signal<string | null>(null);
  showFeatureNotAvailableAlert = signal(false);

  lessons = computed(() => this.course()?.lessons || []);

  constructor() {
    effect(() => {
      const id = this.route.snapshot.paramMap.get('id');
      if (id) {
        const courseId = parseInt(id, 10);
        this.courseId.set(courseId);
        this.loadCourse(courseId);
      }
    });
  }

  private loadCourse(id: number): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    
    this.courseService.loadCourseById(id).subscribe({
      next: (course) => {
        this.course.set(course);
        this.isLoading.set(false);
      },
      error: (error) => {
        this.isLoading.set(false);
        this.errorMessage.set('Failed to load course. Please try again.');
        console.error('Error loading course:', error);
      }
    });
  }

  navigateToEdit(): void {
    if (this.courseId()) {
      this.router.navigate(['/courses', this.courseId(), 'edit']);
    }
  }

  navigateToList(): void {
    this.router.navigate(['/courses']);
  }

  toggleLessonForm(): void {
    this.showLessonForm.update(show => !show);
    this.lessonName.set('');
    this.lessonError.set(null);
  }

  onLessonNameChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.lessonName.set(input.value);
    this.lessonError.set(null);
  }

  addLesson(): void {
    const name = this.lessonName().trim();
    
    if (!name) {
      this.lessonError.set('Lesson name is required');
      return;
    }

    if (this.courseId()) {
      this.courseService.createLesson(this.courseId()!, { name }).subscribe({
        next: () => {
          this.lessonName.set('');
          this.showLessonForm.set(false);
          this.lessonError.set(null);
          // Reload the course to get updated lessons list
          this.loadCourse(this.courseId()!);
        },
        error: (error) => {
          this.lessonError.set('Failed to create lesson. Please try again.');
          console.error('Error creating lesson:', error);
        }
      });
    }
  }

  confirmDeleteLesson(lessonId: number): void {
    this.deleteLessonConfirmId.set(lessonId);
  }

  cancelDeleteLesson(): void {
    this.deleteLessonConfirmId.set(null);
  }

  deleteLesson(lessonId: number): void {
    this.deleteLessonConfirmId.set(null);
    this.showFeatureNotAvailableAlert.set(true);
  }

  closeFeatureNotAvailableAlert(): void {
    this.showFeatureNotAvailableAlert.set(false);
  }

  trackByLessonId(index: number, lesson: Lesson): number {
    return lesson.id;
  }
}
