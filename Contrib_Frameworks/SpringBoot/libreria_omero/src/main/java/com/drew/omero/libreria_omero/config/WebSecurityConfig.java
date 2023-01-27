package com.drew.omero.libreria_omero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	// Come si impostano gli utenti di default
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		UserDetails user2 =
			 User.withDefaultPasswordEncoder()
				.username("guest")
				.password("guest")
				.roles("USER")
				.build();

		UserDetails drew =
				User.withDefaultPasswordEncoder()
				   .username("drew_00")
				   .password("deus_vult")
				   .roles("USER")
				   .build();

		return new InMemoryUserDetailsManager(user, user2, drew);
	}
}
/**
 * Nota --> Cambiando il path del package, prende il sopravvento il sistema di login di default!
 */