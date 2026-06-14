package com.gd0t.gd0t.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${blog.admin.password}")
	private String adminPassword;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/css/**", "/post/{id}", "/resume", "/projects").permitAll()
					.requestMatchers("/new", "/post/*/edit", "/post/*/delete").authenticated()
					.anyRequest().authenticated()
			)
			.formLogin(form -> form
					.defaultSuccessUrl("/", true)
					.permitAll()
			)
			.logout(logout -> logout
					.logoutSuccessUrl("/")
					.permitAll()
					);
				
		return http.build();
	}
	
	
	// For testing purposes only, in production I will use BCrypt TODO: use BCRYPT in production!!!
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails admin = User.builder()
				.username("gd0t")
				.password(encoder.encode(adminPassword))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
