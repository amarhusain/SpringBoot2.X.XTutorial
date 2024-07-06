package com.its.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDTO {

    private String firstname;
    private String lastname;
    private String email;
    private InstructorDetailDTO instructorDetailDTO;
    private List<CourseDTO> courses;

}
