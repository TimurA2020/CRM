package com.example.CRM.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "operators")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Operators {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String surname;
    String department;
}
