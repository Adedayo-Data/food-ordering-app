package com.zosh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resturant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne // One user would have one resturant
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @OneToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;

    @OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL, orphanRemoval = true) // dont create a sperate table for this just go to order and retrieve the data
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    @Column(length = 1000) // by default this would be 255 but the image string is going to be long
    private List<String> images;

    private LocalDateTime registrationDate;

    private boolean open;

    @JsonIgnore // why? Whenver i fetch this object, i don't need the food field inside - we would create a seperate Api for fetching food from any resturant
    @OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL) // One resturant can produce many food
    private List<Food> food = new ArrayList<>();




}
