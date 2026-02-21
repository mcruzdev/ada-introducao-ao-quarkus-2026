import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Course, CreateCourseDto, UpdateCourseDto } from '../models/course.model';
import { Lesson, CreateLessonDto } from '../models/lesson.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080';
  
  private courses = signal<Course[]>([]);

  // Public readonly signals
  readonly allCourses = this.courses.asReadonly();
  readonly coursesCount = computed(() => this.courses().length);

  // CRUD Operations for Courses
  
  getCourses(): Course[] {
    return this.courses();
  }

  loadCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiUrl}/courses`).pipe(
      tap(courses => this.courses.set(courses))
    );
  }

  getCourseById(id: number): Course | undefined {
    return this.courses().find(course => course.id === id);
  }

  loadCourseById(id: number): Observable<Course> {
    return this.http.get<Course>(`${this.apiUrl}/courses/${id}`).pipe(
      tap(course => {
        this.courses.update(courses => {
          const index = courses.findIndex(c => c.id === id);
          if (index !== -1) {
            const updated = [...courses];
            updated[index] = course;
            return updated;
          }
          return [...courses, course];
        });
      })
    );
  }

  createCourse(dto: CreateCourseDto): Observable<Course> {
    return this.http.post<Course>(`${this.apiUrl}/courses`, dto).pipe(
      tap(newCourse => {
        this.courses.update(courses => [...courses, newCourse]);
      })
    );
  }

  updateCourse(id: number, dto: UpdateCourseDto): Observable<Course> {
    return this.http.put<Course>(`${this.apiUrl}/courses/${id}`, dto).pipe(
      tap(updatedCourse => {
        this.courses.update(courses => {
          return courses.map(c => c.id === id ? updatedCourse : c);
        });
      })
    );
  }

  deleteCourse(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/courses/${id}`).pipe(
      tap(() => {
        this.courses.update(courses => courses.filter(c => c.id !== id));
      })
    );
  }

  // CRUD Operations for Lessons within a Course

  getLessonsByCourseId(courseId: number): Lesson[] {
    const course = this.getCourseById(courseId);
    return course?.lessons || [];
  }

  createLesson(courseId: number, dto: CreateLessonDto): Observable<Lesson> {
    return this.http.post<Lesson>(`${this.apiUrl}/courses/${courseId}/lessons`, dto).pipe(
      tap(newLesson => {
        this.courses.update(courses => {
          return courses.map(c => {
            if (c.id === courseId) {
              return {
                ...c,
                lessons: [...c.lessons, newLesson]
              };
            }
            return c;
          });
        });
      })
    );
  }

  deleteLesson(courseId: number, lessonId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/courses/${courseId}/lessons/${lessonId}`).pipe(
      tap(() => {
        this.courses.update(courses => {
          return courses.map(c => {
            if (c.id === courseId) {
              return {
                ...c,
                lessons: c.lessons.filter(l => l.id !== lessonId)
              };
            }
            return c;
          });
        });
      })
    );
  }
}
