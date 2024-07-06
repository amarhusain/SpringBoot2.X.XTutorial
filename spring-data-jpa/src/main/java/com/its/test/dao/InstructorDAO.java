package com.its.test.dao;

import com.its.test.dto.InstructorDTO;
import com.its.test.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface InstructorDAO {

   List<Instructor> getAllInstructors();
   Instructor createInstructor(Instructor instructor);
   Instructor createInstructorWithCourses(InstructorDTO instructorDTO);
   Instructor findInstructorById(Long id);
   Instructor findInstructorByIdJoinFetch(Long instructorId);
   InstructorDetail findInstructorDetailById(Long id);
   Instructor updateInstructor(Long instructorId, Instructor instructor);
   boolean deleteInstructor(Long instructorId);
   boolean deleteInstructorDetail(Long instructorDetailId);


   
}
