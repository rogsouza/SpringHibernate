package com.example.SpringHibernate.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringHibernate.StatusRole;
import com.example.SpringHibernate.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByStatus(StatusRole status);
	List<Role> findListByStatus(StatusRole status);
	
	// Paginar a busca no banco de dados
	Page<Role> findAll(Pageable pageable);
}
