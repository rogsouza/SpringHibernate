package com.example.SpringHibernate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringHibernate.Entity.Functionality;

public interface FunctionalityRepository extends JpaRepository <Functionality, Long> {

}
