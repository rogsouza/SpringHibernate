package com.example.SpringHibernate;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.SpringHibernate.Entity.Functionality;
import com.example.SpringHibernate.Entity.Role;
import com.example.SpringHibernate.Entity.Role2;
import com.example.SpringHibernate.Entity.User;
import com.example.SpringHibernate.Entity.User2;
import com.example.SpringHibernate.Repository.FunctionalityRepository;
import com.example.SpringHibernate.Repository.Role2Repository;
import com.example.SpringHibernate.Repository.RoleRepository;
import com.example.SpringHibernate.Repository.User2Repository;
import com.example.SpringHibernate.Repository.UserRepository;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private User2Repository user2Repository;
	
	@Autowired
	private Role2Repository role2Repository;
	
	@Autowired
	private FunctionalityRepository funcionalityRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		Role role = new Role();
		role.setName("Admin");
		role.setStatus(StatusRole.ATIVO);
		
		Role role2 = new Role();
		role2.setName("Student");
		role2.setStatus(StatusRole.ATIVO);

		Role role3 = new Role();
		role3.setName("Manager");
		role3.setStatus(StatusRole.INATIVO);

		// esta usando CascadeType.PERSIST entao não preciso salvar as roles antes
		// Pendente: Quando foi cadastrar um usuario com uma ROLE que ja existe deu pau
		this.roleRepository.save(role);
		this.roleRepository.save(role2);
		this.roleRepository.save(role3);

		User user = new User();
		user.setName("Rogerio Arcanjo");
		user.setEmail("radsouza@gmail.com");
		user.setRole(role);
		
		User user2 = new User();
		user2.setName("Marcia Gravina");
		user2.setEmail("marcia.gravina@gmail.com");
		user2.setRole(role2);

		User user3 = new User();
		user3.setName("Jose Verdan");
		user3.setEmail("jose.verdan@gmail.com");
		user3.setRole(role2);
		//user3.setRole(role3);
		
		this.userRepository.save(user);
		this.userRepository.save(user2);
		this.userRepository.save(user3);

		List<Role> roles = this.roleRepository.findListByStatus(StatusRole.ATIVO);
		for (Role irole : roles) {
			System.out.println("role ativa - " + irole.getName());
		}
		
		roles = this.roleRepository.findListByStatus(StatusRole.INATIVO);
		for (Role irole : roles) {
			System.out.println("role desativada - " + irole.getName());
		}
		
		for (int i = 0; i < 1000; i++) {
			StatusRole status = StatusRole.ATIVO;
			if ((i % 2) == 0) {
				status = StatusRole.INATIVO;
			}
			this.saveRole("Test " + i, status);
		}
		
		// Determina o tamanho do fetch ao buscar os registros no banco de dados
		
		PageRequest page = PageRequest.of(10,10);
		Page<Role> roles2 = this.roleRepository.findAll(page);
		for (Role irole : roles2) {
			System.out.println(irole.getName());
		}
		
		/*=================================================================*/
		
		System.out.println("==================================================================");
		System.out.println("Relacionamento 1 para N - Um usuario com varios perfils (role)...");
		
		Functionality functionality1 = new Functionality();
		functionality1.setName("Add");

		Functionality functionality2 = new Functionality();
		functionality2.setName("Delete");

		Functionality functionality3 = new Functionality();
		functionality3.setName("Report");

		Functionality functionality4 = new Functionality();
		functionality4.setName("All");
		
		/* PENDENTE - SE EU TENTAR INSERIR A MESMA FUNCIONALIDADE EM 2 PERFIS DIFERENTE DA PAU DE CHAVE PRIMARIA VIOLADA POR A FUNCIONALIDADE JA FOI INSERIDA ANTES */
		// Para contornar isto acho que tem que fazer o save separado incluindo o relacionamento ???? Analisar depois
		
		Role2 role20 = new Role2();
		role20.setName("Admin");
		role20.setStatus(StatusRole.ATIVO);
		//role20.setFunctionalities(Arrays.asList(functionality1, functionality2, functionality3));
		role20.setFunctionalities(Arrays.asList(functionality1, functionality2));
		
		Role2 role21 = new Role2();
		role21.setName("Student");
		role21.setStatus(StatusRole.ATIVO);		
		role21.setFunctionalities(Arrays.asList(functionality3));	
		
		Role2 role22 = new Role2();
		role22.setName("Teacher");
		role22.setStatus(StatusRole.ATIVO);		
		role22.setFunctionalities(Arrays.asList(functionality4));		
		
		// esta usando CascadeType.PERSIST entao não preciso salvar as roles antes
		
		User2 user20 = new User2();
		user20.setName("Ely Gravina");
		user20.setEmail("ely@gmail.com");
		user20.setRoles(Arrays.asList(role20, role21));
		
		User2 user21 = new User2();
		user21.setName("Zeth Arcanjo");
		user21.setEmail("zeth@gmail.com");
		user21.setRoles(Arrays.asList(role22));
		
		this.user2Repository.save(user20);
		this.user2Repository.save(user21);
		
		System.out.println(">>>> Percorrento todos os usuarios <<<<");
		List<User2> userR = this.user2Repository.findAll();
		for (User2 iuser : userR) {
			System.out.println("User -> " + iuser.getName());			
			for (Role2 irole : iuser.getRoles()) {
				System.out.println("Perfil -> " + irole.getName());
				for (Functionality ifunc : irole.getFunctionalities()) {
					System.out.println("Funcionalidade -> " + ifunc.getName());
				}
			}
		}

		System.out.println(">>>> Percorrento apenas 1 usuario - findByName (ZETH) <<<<");
		User2 userR2 = this.user2Repository.findByName("Zeth Arcanjo");
		System.out.println("User -> " + userR2.getName());			
		for (Role2 irole : userR2.getRoles()) {
			System.out.println("Perfil -> " + irole.getName());
			for (Functionality ifunc : irole.getFunctionalities()) {
				System.out.println("Funcionalidade -> " + ifunc.getName());
			}
		}
		
		System.out.println(">>>> Percorrento apenas 1 usuario - findByEmail (ZETH) <<<<");
		userR2 = this.user2Repository.findByEmail("zeth@gmail.com");
		System.out.println("User -> " + userR2.getName());			
		for (Role2 irole : userR2.getRoles()) {
			System.out.println("Perfil -> " + irole.getName());
			for (Functionality ifunc : irole.getFunctionalities()) {
				System.out.println("Funcionalidade -> " + ifunc.getName());
			}
		}		
		
		System.out.println(">>>> Percorrento apenas 1 usuario - findByNameLike (ZETH) <<<<");
		userR2 = this.user2Repository.findByNameLike2("Zeth");
		System.out.println("User -> " + userR2.getName());			
		for (Role2 irole : userR2.getRoles()) {
			System.out.println("Perfil -> " + irole.getName());
			for (Functionality ifunc : irole.getFunctionalities()) {
				System.out.println("Funcionalidade -> " + ifunc.getName());
			}
		}			
		
	}
	
	public void saveRole(String name, StatusRole status) {
		Role role = new Role(name, status);
		this.roleRepository.save(role);
	}

}
