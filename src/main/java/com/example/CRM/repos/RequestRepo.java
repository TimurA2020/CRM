package com.example.CRM.repos;

import com.example.CRM.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RequestRepo extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.handled = false")
    List<Request> limit();

    List<Request> getAllByHandledTrue();
}
