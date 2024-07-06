package com.its.test.dao;

import com.its.test.entity.Course;
import com.its.test.entity.Student;

public interface CourseDAO {

    Course findCourseById(Long id);

    void saveCourse(Course course);
    Course findCourseAndReviewsByCourseId(Long id);

    boolean deleteCourseById(Long id);

    void createCourseAndStudent(Course course);

    Course findCourseAndStudentsByCourseId(Long id);

    Student findStudentAndCoursesByStudentId(Long id);

    void updateStudent(Student student);

}
