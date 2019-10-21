package com.example.SpringHibernate.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String email;
	
	@ManyToOne
	//@ManyToOne(cascade = CascadeType.PERSIST)  // Não precisa salvar antes as entidades que tem relacionamento. No momento da inserção do filho o dominio é cadastrado
	//@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) // Nao carrega os objetos (tabelas) relacionadas
	//@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // Carrega os objetos (tabelas) relacionadas - É o default
	private Role role;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
