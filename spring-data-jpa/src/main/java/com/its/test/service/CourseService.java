package com.its.test.service;

import com.its.test.dto.CourseDTO;
import com.its.test.entity.Course;
import com.its.test.entity.Student;

import java.util.List;

public interface CourseService {


    Course createCourse(CourseDTO course);


    Course retrieveCourseAndReviews(Long id);

    boolean deleteCourseById(Long id);

    Course createCourseAndStudent(CourseDTO courseDTO);

    Course findCourseAndStudentsByCourseId(Long id);

    Student findStudentAndCoursesByStudentId(Long id);

    boolean updateStudent(List<CourseDTO> course, Long id);
}
