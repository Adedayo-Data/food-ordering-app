package com.zosh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long price;

    @ManyToOne
    private Category foodCategory;

    @Column(length = 1000)
    @ElementCollection // This is used to create a seperate table for collections of common types
    private List<String> images;

    private boolean available;

    @ManyToOne // Much food can be produced in a single resturant
    private Resturant resturant;

    private  boolean isVegetarian;
    private boolean isSeasonal;

    @ManyToMany // Many ingredients would be used to create a food and a food can also be an ingredient for other food
    private List<IngredientItem> ingredients = new ArrayList<>(); // This is required to make an empty list instead of null

    private Date creationDate;
}
