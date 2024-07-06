package com.its.test.dao;

import com.its.test.entity.Course;
import com.its.test.entity.Student;
import com.its.test.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private final EntityManager entityManager;

    public CourseDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Course findCourseById(Long id) {
        return entityManager.find(Course.class , id);
    }

    @Override
    public void saveCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(Long id) {
        TypedQuery<Course> query =  entityManager.createQuery("select c from Course c "
                +"JOIN FETCH c.reviews "
                +" where c.id = :id", Course.class);
        query.setParameter("id", id);
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    @Transactional
    public boolean deleteCourseById(Long id) {
        Course course = entityManager.find(Course.class, id);
        if(course == null) {
            throw new ResourceNotFoundException("Course not found");
        }
        entityManager.remove(course);
        return true;
    }

    @Override
    @Transactional
    public void createCourseAndStudent(Course course) {
        entityManager.persist(course);

    }

    @Override
    public Course findCourseAndStudentsByCourseId(Long id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "JOIN FETCH c.students "
                        +"where c.id = :id", Course.class
        );
        query.setParameter("id", id);
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(Long id) {
        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student s "
                        + "JOIN FETCH s.courses "
                        +"where s.id = :id", Student.class
        );
        query.setParameter("id", id);
        Student student = query.getSingleResult();
        return student;
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
            entityManager.merge(student);
    }
}
