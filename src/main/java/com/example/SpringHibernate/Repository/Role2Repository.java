package com.example.SpringHibernate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringHibernate.StatusRole;
import com.example.SpringHibernate.Entity.Role2;

@Repository
public interface Role2Repository extends JpaRepository<Role2, Long> {

	Role2 findByStatus(StatusRole status);
	List<Role2> findListByStatus(StatusRole status);
	
}
