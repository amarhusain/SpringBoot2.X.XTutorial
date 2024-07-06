package com.its.test.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//With @JsonIdentityInfo, Jackson will serialize the objects by their identity, ensuring that the relationship is bidirectional and avoiding infinite recursion.

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "youtube_channel")
    private String youtubeChannel;

    @Column(name = "hobby")
    private String hobby;

    @OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Instructor instructor;

}
