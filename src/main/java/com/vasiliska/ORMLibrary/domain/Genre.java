package com.vasiliska.ORMLibrary.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "genre_name")
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre() {
    }
}
