package com.its.test.service;


import com.its.test.dto.CourseDTO;
import com.its.test.dto.InstructorDTO;
import com.its.test.entity.Course;
import com.its.test.entity.Instructor;
import com.its.test.entity.InstructorDetail;
import com.its.test.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InstructorService {

    Instructor createInstructor(Instructor instructor);

    Instructor createInstructorWithCourses(InstructorDTO instructorDTO);

    List<Instructor> getAllInstructors();

    Instructor findInstructorById(Long id);

    Instructor findInstructorByIdJoinFetch(Long id);

    InstructorDetail findInstructorDetailById(Long id);

    Instructor updateInstructor(Long instructorId, Instructor instructor);

    boolean deleteInstructorById(Long id);

    boolean deleteInstructorDetailById(Long id);


}
