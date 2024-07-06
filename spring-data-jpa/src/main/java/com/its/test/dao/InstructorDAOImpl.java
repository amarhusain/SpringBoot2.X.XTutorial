package com.its.test.dao;

import com.its.test.dto.CourseDTO;
import com.its.test.dto.InstructorDTO;
import com.its.test.entity.Course;
import com.its.test.entity.Instructor;
import com.its.test.entity.InstructorDetail;
import com.its.test.entity.Student;
import com.its.test.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class InstructorDAOImpl implements InstructorDAO{

    private final EntityManager entityManager;

    public InstructorDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public List<Instructor> getAllInstructors() {
//        String jpql = "SELECT i FROM Instructor i";
//        TypedQuery<Instructor> query = entityManager.createQuery(jpql, Instructor.class);
//
//        return query.getResultList();
        return entityManager.createQuery("from Instructor", Instructor.class).getResultList();

    }

    @Override
    @Transactional
    public Instructor createInstructor(Instructor instructor) {
        entityManager.persist(instructor);
        return instructor;
    }

    @Override
    @Transactional
    public Instructor createInstructorWithCourses(InstructorDTO instructorDTO) {
        Instructor tempInstructor = getInstructor(instructorDTO);
        entityManager.persist(tempInstructor);
        return tempInstructor;
    }

    @Override
    @Transactional
    public Instructor findInstructorById(Long id) {
//        TypedQuery<Instructor> query = entityManager.createQuery("from Instructor where id = :id", Instructor.class);
//        query.setParameter("id", id);
//        Instructor instructor = query.getSingleResult();
//        return Optional.ofNullable(instructor);
        return entityManager.find(Instructor.class, id);
    }

    private static Instructor getInstructor(InstructorDTO instructorDTO) {
        InstructorDetail  tempInstructorDetail = new InstructorDetail();
        tempInstructorDetail.setYoutubeChannel(instructorDTO.getInstructorDetailDTO().getYoutubeChannel());
        tempInstructorDetail.setHobby(instructorDTO.getInstructorDetailDTO().getHobby());

        Instructor tempInstructor = new Instructor();
        tempInstructor.setFirstname(instructorDTO.getFirstname());
        tempInstructor.setLastname(instructorDTO.getLastname());
        tempInstructor.setEmail(instructorDTO.getEmail());
        tempInstructor.setInstructorDetail(tempInstructorDetail);

        for(CourseDTO c : instructorDTO.getCourses()){
            Course tempCourse = new Course();
            tempCourse.setTitle(c.getTitle());
            tempInstructor.addCourse(tempCourse);
        }
        return tempInstructor;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(Long instructorId) {
        // create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i "
                        + "JOIN FETCH i.courses "
                        +"JOIN FETCH i.instructorDetail "
                        +" where i.id = :instructorId", Instructor.class);
        query.setParameter("instructorId", instructorId);

        // execute query
        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    public InstructorDetail findInstructorDetailById(Long id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public Instructor updateInstructor(Long instructorId, Instructor newInstructor) {
        Instructor oldInstructor = entityManager.find(Instructor.class , instructorId);
        if(oldInstructor != null) {
            oldInstructor.setFirstname(newInstructor.getFirstname());
            oldInstructor.setLastname(newInstructor.getLastname());
            oldInstructor.setEmail(newInstructor.getEmail());
            entityManager.merge(oldInstructor);
            return oldInstructor;
        }else{
            return null;
        }

    }

    @Override
    @Transactional
    public boolean deleteInstructor(Long instructorId) {
        Instructor instructor = entityManager.find(Instructor.class , instructorId);
        if(instructor != null) {
            entityManager.remove(instructor);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteInstructorDetail(Long instructorDetailId) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class , instructorDetailId);
        if(instructorDetail != null) {
            entityManager.remove(instructorDetail);
            return true;
        }else {
            return false;
        }
    }




}
