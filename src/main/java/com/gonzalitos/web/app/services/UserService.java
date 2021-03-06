package com.gonzalitos.web.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gonzalitos.web.app.converters.UserConverter;
import com.gonzalitos.web.app.entities.User;
import com.gonzalitos.web.app.enums.Roles;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.UserModel;
import com.gonzalitos.web.app.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private UserRepository userRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public User save(UserModel model) throws WebException {
		User user = userConverter.modelToEntity(model);
		if (user.getRemove() != null) {
			throw new WebException("El agresor que intenta modificar se encuentra dada de baja.");
		}
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new WebException("El nombre del agresor no puede estar vacio.");
		}		
		return userRepository.save(user);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public User delete(String id) throws WebException {
		User financiamiento = userRepository.getOne(id);
		if (financiamiento.getRemove() == null) {
			financiamiento.setRemove(new Date());
			financiamiento = userRepository.save(financiamiento);
		} else {
			throw new WebException("El Usuario que intenta eliminar ya se encuentra dado de baja.");
		}
		return financiamiento;
	}
	
	public UserModel search(String id) {
		User user = userRepository.getOne(id);
		return userConverter.entityToModel(user);
	}
		
	public User authentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.searchByEmail(auth.getName());
	}

	@Override
	public UserDetails loadUserByUsername(String email)  {
		User user = userRepository.searchByEmail(email);
		if (user != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());
			permissions.add(p);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("user", user);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permissions);
		} else {
			return null;
		}
	}
	
	public Boolean Scrap() {
	    try {
	        Document doc = Jsoup.connect("https://www.dateas.com/es/consulta_cuit_cuil?name=&cuit=36417168&tipo=fisicas-juridicas").get();
//	        String name=;
//	        String dni=;
	        System.out.println(doc.getElementsByTag("tr").select(".odd").select("a").get(0));
	        return true;
	        
	      } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	      }
	}
	
	public void lilith() {
		if (userRepository.searchByEmail("gonzalitos@gmail.com")==null) {
			User user = new User();
			user.setDni("35555555");
			user.setEmail("gonzalitos@gmail.com");
			user.setName("Gonzalo");
			user.setLastName("Sarmiento");
			user.setRole(Roles.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("asdasdasd"));
			userRepository.save(user);
		}
	}
	
}