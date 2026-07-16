package com.myy803.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.myy803.project.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

		@Autowired
		private CustomUserDetailsService customUserDetailsService;
		
		@Bean
		public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
			AuthenticationManagerBuilder auth =
				http.getSharedObject(AuthenticationManagerBuilder.class);
			auth.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
			return auth.build();
		}
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/register", "/login", "/css/**").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/dashboard", true)
					.permitAll()
				)
				.logout(logout -> logout
					.logoutUrl("/logout")	
					.logoutSuccessUrl("/login")
					.permitAll()
				);
			return http.build();
		}
		
}
