package com.example.CRM.repos;

import com.example.CRM.models.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OperatorRepo extends JpaRepository<Operators, Long> {

}
