import { Component, ChangeDetectionStrategy, inject, signal, effect } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [ReactiveFormsModule]
})
export class CourseFormComponent {
  private courseService = inject(CourseService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  courseId = signal<number | null>(null);
  isEditMode = signal(false);
  errorMessage = signal<string | null>(null);
  isLoading = signal(false);
  isSubmitting = signal(false);

  courseForm: FormGroup = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(3)]]
  });

  constructor() {
    effect(() => {
      const id = this.route.snapshot.paramMap.get('id');
      if (id && id !== 'new') {
        const courseId = parseInt(id, 10);
        this.courseId.set(courseId);
        this.isEditMode.set(true);
        this.loadCourse(courseId);
      }
    });
  }

  private loadCourse(id: number): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    
    this.courseService.loadCourseById(id).subscribe({
      next: (course) => {
        this.courseForm.patchValue({
          name: course.name
        });
        this.isLoading.set(false);
      },
      error: (error) => {
        this.isLoading.set(false);
        this.errorMessage.set('Failed to load course. Please try again.');
        console.error('Error loading course:', error);
      }
    });
  }

  onSubmit(): void {
    if (this.courseForm.invalid) {
      this.courseForm.markAllAsTouched();
      return;
    }

    const formValue = this.courseForm.value;
    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    if (this.isEditMode() && this.courseId() !== null) {
      this.courseService.updateCourse(this.courseId()!, {
        name: formValue.name
      }).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.router.navigate(['/courses']);
        },
        error: (error) => {
          this.isSubmitting.set(false);
          this.errorMessage.set('Failed to update course. Please try again.');
          console.error('Error updating course:', error);
        }
      });
    } else {
      this.courseService.createCourse({
        name: formValue.name
      }).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.router.navigate(['/courses']);
        },
        error: (error) => {
          this.isSubmitting.set(false);
          this.errorMessage.set('Failed to create course. Please try again.');
          console.error('Error creating course:', error);
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/courses']);
  }

  getFieldError(fieldName: string): string | null {
    const field = this.courseForm.get(fieldName);
    if (field?.invalid && (field.dirty || field.touched)) {
      if (field.errors?.['required']) {
        return 'This field is required';
      }
      if (field.errors?.['minlength']) {
        const minLength = field.errors['minlength'].requiredLength;
        return `Minimum length is ${minLength} characters`;
      }
    }
    return null;
  }

  hasFieldError(fieldName: string): boolean {
    return this.getFieldError(fieldName) !== null;
  }
}
