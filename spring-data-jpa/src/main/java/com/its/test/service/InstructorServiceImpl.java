package com.its.test.service;


import com.its.test.dao.InstructorDAO;
import com.its.test.dto.CourseDTO;
import com.its.test.dto.InstructorDTO;
import com.its.test.dto.ReviewDTO;
import com.its.test.dto.StudentDTO;
import com.its.test.entity.*;
import com.its.test.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService{

    private final InstructorDAO instructorDAO;

    public InstructorServiceImpl(InstructorDAO instructorDAO) {
        this.instructorDAO = instructorDAO;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorDAO.getAllInstructors();
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        return instructorDAO.createInstructor(instructor);
    }

    @Override
    @Transactional
    public Instructor createInstructorWithCourses(InstructorDTO instructorDTO) {
        return instructorDAO.createInstructorWithCourses(instructorDTO);
    }

    @Override
    @Transactional
    public Instructor findInstructorById(Long instructorId) {

        // find the instructor
        Instructor instructor = instructorDAO.findInstructorById(instructorId);
        if(instructor == null) {
            throw new ResourceNotFoundException("Instructor not found");
        }
        return instructor;
//        return instructor.orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
    }

    @Override
    @Transactional
    public Instructor findInstructorByIdJoinFetch(Long id) {
//        Instructor instructor = instructorDAO.findInstructorById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));;
        Instructor instructor = instructorDAO.findInstructorById(id);
        if(instructor == null) {
            throw new ResourceNotFoundException("Instructor not found");
        }
        return instructorDAO.findInstructorByIdJoinFetch(instructor.getId());
    }

    @Override
    public InstructorDetail findInstructorDetailById(Long id) {
        InstructorDetail instructorDetail = instructorDAO.findInstructorDetailById(id);
        if (instructorDetail == null) {
            throw new ResourceNotFoundException("Instructor not found");
        }
        return instructorDetail;
    }

    @Override
    public Instructor updateInstructor(Long instructorId, Instructor newInstructor) {
        return instructorDAO.updateInstructor(instructorId, newInstructor);
    }

    @Override
    public boolean deleteInstructorById(Long id) {
        boolean result = instructorDAO.deleteInstructor(id);
        if(result) {
            return true;
        }else {
            throw new ResourceNotFoundException("No account found for id " + id);
        }
    }

    @Override
    public boolean deleteInstructorDetailById(Long id) {
        boolean result = instructorDAO.deleteInstructorDetail(id);
        if(result) {
            return true;
        }else {
            throw new ResourceNotFoundException("No account found for id " + id);
        }
    }



}
