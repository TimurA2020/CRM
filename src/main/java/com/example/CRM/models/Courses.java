package com.example.CRM.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    int price;
}
