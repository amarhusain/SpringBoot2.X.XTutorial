package com.its.test.controller;

import com.its.test.dto.CourseDTO;
import com.its.test.entity.Course;
import com.its.test.entity.Student;
import com.its.test.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "API for managing courses", description="")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        Course savedCourse = courseService.createCourse(courseDTO);
        if (savedCourse!= null) {
            return ResponseEntity.ok().body(savedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-course-student")
    public ResponseEntity<Course> createCourseAndStudent(@RequestBody CourseDTO courseDTO) {
        Course savedCourse = courseService.createCourseAndStudent(courseDTO);
        if (savedCourse!= null) {
            return ResponseEntity.ok().body(savedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> retrieveCourseAndReviews(@PathVariable(value = "id") Long id) {
        Course course = courseService.retrieveCourseAndReviews(id);
        if (course!= null) {
            return ResponseEntity.ok().body(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/course-student/{id}")
    public ResponseEntity<Course> retrieveCourseAndStudents(@PathVariable(value = "id") Long id) {
        Course course = courseService.findCourseAndStudentsByCourseId(id);
        if (course!= null) {
            return ResponseEntity.ok().body(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student-course/{id}")
    public ResponseEntity<Student> retrieveStudentAndCourses(@PathVariable(value = "id") Long id) {
        Student student = courseService.findStudentAndCoursesByStudentId(id);
        if (student!= null) {
                return ResponseEntity.ok().body(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable(value = "id") Long id) {
        boolean result = courseService.deleteCourseById(id);
        if (result) {
            return ResponseEntity.ok().body("Course deleted successfully.");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/add-courses-to-student/{id}")
    public ResponseEntity<String> addCoursesToStudent(@RequestBody List<CourseDTO> courses, @PathVariable(value = "id") Long id) {
        boolean result = courseService.updateStudent(courses, id);
        if (result) {
            return ResponseEntity.ok().body("course added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
