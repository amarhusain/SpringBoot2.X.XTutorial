package com.its.test.service;

import com.its.test.dao.CourseDAO;
import com.its.test.dto.CourseDTO;
import com.its.test.dto.ReviewDTO;
import com.its.test.dto.StudentDTO;
import com.its.test.entity.Course;
import com.its.test.entity.Review;
import com.its.test.entity.Student;
import com.its.test.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO;

    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
    
    @Override
    @Transactional
    public Course createCourse(CourseDTO courseDTo) {
        Course tempCourse = new Course();
        tempCourse.setTitle(courseDTo.getTitle());
        for(ReviewDTO reviewDTO : courseDTo.getReviews()) {
            Review review = new Review();
            review.setComment(reviewDTO.getComment());
            tempCourse.addReview(review);
        }
        courseDAO.saveCourse(tempCourse);
        return tempCourse;
    }

    @Override
    public Course retrieveCourseAndReviews(Long id) {
        Course course = courseDAO.findCourseAndReviewsByCourseId(id);
        if(course == null) {
            throw new ResourceNotFoundException("Course not found");
        }
        return course;
    }

    @Override
    public boolean deleteCourseById(Long id) {
        return  courseDAO.deleteCourseById(id);
    }

    @Override
    public Course createCourseAndStudent(CourseDTO courseDTO) {

        Course tempCourse = new Course();
        tempCourse.setTitle(courseDTO.getTitle());
        for(StudentDTO studentDTO: courseDTO.getStudents()){
            Student student = new Student();
            student.setFirstname(studentDTO.getFirstname());
            student.setLastname(studentDTO.getLastname());
            student.setEmail(studentDTO.getEmail());
            tempCourse.addStudent(student);
        }
        courseDAO.createCourseAndStudent(tempCourse);
        return tempCourse;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(Long id) {
        Course course = courseDAO.findCourseAndStudentsByCourseId(id);
        if(course == null) {
            throw new ResourceNotFoundException("Course not found");
        }
        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(Long id) {
        Student student = courseDAO.findStudentAndCoursesByStudentId(id);
        if(student == null) {
            throw new ResourceNotFoundException("Student not found");
        }
        return student;
    }

    @Override
    public boolean updateStudent(List<CourseDTO> courses, Long id) {
        Student tempStudent = findStudentAndCoursesByStudentId(id);
        if(tempStudent == null) {
            throw new ResourceNotFoundException("Student not found");
        }

        for(CourseDTO courseDTO : courses){
            Course tempCourse = new Course();
            tempCourse.setTitle(courseDTO.getTitle());
            tempStudent.addCourse(tempCourse);
        }

        courseDAO.updateStudent(tempStudent);
        return true;
    }

}
