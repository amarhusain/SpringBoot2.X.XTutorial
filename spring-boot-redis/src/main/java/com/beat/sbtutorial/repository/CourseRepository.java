package com.beat.sbtutorial.repository;

import com.beat.sbtutorial.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
