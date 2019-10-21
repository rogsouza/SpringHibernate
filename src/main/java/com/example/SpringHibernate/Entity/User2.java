package com.example.SpringHibernate.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User2 {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String email;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // fetch necessario para fazer o join. Neste tipo de relacionamento nao eh default. Senao nao faz o join e da pau se voce tentar acessar a informacao de outra tabela
	private List<Role2> roles;

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

	public List<Role2> getRoles() {
		return roles;
	}

	public void setRoles(List<Role2> roles) {
		this.roles = roles;
	}

}
