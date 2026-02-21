import { Lesson } from './lesson.model';

export interface Course {
  id: number;
  name: string;
  lessons: Lesson[];
}

export interface CreateCourseDto {
  name: string;
}

export interface UpdateCourseDto {
  name: string;
}
