package com.gonzalitos.web.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gonzalitos.web.app.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("userService")
	public UserService userService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).
		passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()

				.antMatchers("/css/*", "/js/*", "/img/*", "/**","/main/**").permitAll()
				.and().formLogin()
					.loginPage("/login")
						.loginProcessingUrl("/logincheck")
						.usernameParameter("email")
						.passwordParameter("password")
						.defaultSuccessUrl("/loginsuccess")
						.failureUrl("/login")
						.permitAll()
				.and().logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/main")
					.permitAll()
				.and().csrf()
					.disable();
	}
	
	public static void main(String[] arg) {
		String pass = new BCryptPasswordEncoder().encode("1234");
		System.out.println(pass);
	}

}
