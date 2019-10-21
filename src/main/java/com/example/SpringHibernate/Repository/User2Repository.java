package com.example.SpringHibernate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SpringHibernate.Entity.User2;

@Repository
public interface User2Repository extends JpaRepository<User2, Long> {
	
	User2 findByName(String name);
	User2 findByEmail(String email);
	
	@Query("select u from User2 u where u.name like %?1%")     /* ?1 ==> ? indica variavel e o 1 indica o primeiro parametro - Nome da tabela tem que ser igual ao nome da classe */
	User2 findByNameLike2(String name);
}
