package com.dzco.hibernatepractice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id",
                        foreignKey = @ForeignKey(name = "fk_author_id"))
    private Author author;
}
