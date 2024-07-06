package com.its.test.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_table")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_year")
    private String year;

}
