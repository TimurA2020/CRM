package com.example.CRM.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table (name = "requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userName;

    String comment;

    String phone;

    boolean handled;

    @ManyToOne(fetch = FetchType.LAZY)
    Courses course;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Operators> operators;
}
