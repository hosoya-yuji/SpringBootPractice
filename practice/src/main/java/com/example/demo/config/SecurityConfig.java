package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.CustomAuthenticationFailureHandler;
import com.example.demo.service.AdminDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	 private final AdminDetailsService adminDetailsService;

	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Autowired
	public SecurityConfig(AdminDetailsService adminDetailsService) {
		this.adminDetailsService = adminDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(adminDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.debug("Configuring security filter chain");

		http
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/admin/signup").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
			)
			.formLogin(formLogin ->
			formLogin
					.loginPage("/admin/signin")
					.loginProcessingUrl("/admin/signin")
					.defaultSuccessUrl("/admin/contacts")
					.failureUrl("/admin/signin?error=true")
					.failureHandler(customAuthenticationFailureHandler) // カスタムハンドラを追加
					.permitAll()
					.usernameParameter("email")
					.passwordParameter("password")
			)
			.logout(logout ->
				logout
					.logoutUrl("/admin/logout")
					.logoutSuccessUrl("/admin/signin")
					.permitAll()
			)
			.csrf(csrf -> csrf.disable());

		logger.debug("Security filter chain configured");

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return adminDetailsService;
	}
}
