package com.project.passengerflow.repository;

import com.project.passengerflow.model.PathPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathPartRepository extends JpaRepository<PathPart, Integer> {
}
