package com.its.test.controller;


import com.its.test.dto.InstructorDTO;
import com.its.test.entity.Instructor;
import com.its.test.entity.InstructorDetail;
import com.its.test.service.InstructorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@Tag(name = "API for managing instructors", description="")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // API 2
    @GetMapping
    public List<Instructor> getInstructors() {
        return instructorService.getAllInstructors();
    }

    // API 3
    @PostMapping
    public Instructor createInstructor( @RequestBody Instructor instructor) {
         return  instructorService.createInstructor(instructor);
    }

    @PostMapping("/instructor-course")
    public Instructor createInstructorWithCourses( @RequestBody InstructorDTO instructorDTO) {
        return  instructorService.createInstructorWithCourses(instructorDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable(value = "id") Long instructorId) {
        Instructor instructor = instructorService.findInstructorById(instructorId);
        instructor.setCourses(null);
        if (instructor!= null) {
            return ResponseEntity.ok().body(instructor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/instructor-course/{id}")
    public ResponseEntity<Instructor> getInstructorByIdWithCourse(@PathVariable(value = "id") Long instructorId) {
        Instructor instructor = instructorService.findInstructorByIdJoinFetch(instructorId);
        if (instructor!= null) {
            return ResponseEntity.ok().body(instructor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/instructor-detail/{id}")
    public ResponseEntity<Object> getInstructorDetailById(@PathVariable(value = "id") Long id) {
        InstructorDetail result = instructorService.findInstructorDetailById(id);
//        return ResponseEntity.ok().body("executed");
        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable(value = "id") Long instructorId, @RequestBody Instructor instructor) {
        Instructor updatedInstructor = instructorService.updateInstructor(instructorId, instructor);
        if (updatedInstructor != null) {
            return ResponseEntity.ok().body(updatedInstructor);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable(value = "id") Long instructorId) {
        boolean result = instructorService.deleteInstructorById(instructorId);
        if (result) {
            return ResponseEntity.ok().body("Instructor deleted successfully.");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/instructor-detail/{id}")
    public ResponseEntity<String> deleteInstructorDetail(@PathVariable(value = "id") Long instructorId) {
        boolean result = instructorService.deleteInstructorDetailById(instructorId);
        if (result) {
            return ResponseEntity.ok().body("Instructor detail deleted successfully.");
        }else {
            return ResponseEntity.notFound().build();
        }
    }



}
