package com.pawasa.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "parent_id")
    private Long parentID;

    public Category() {
    }

    public Category(Long id, String categoryName, Long parentID) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentID = parentID;
    }
}
