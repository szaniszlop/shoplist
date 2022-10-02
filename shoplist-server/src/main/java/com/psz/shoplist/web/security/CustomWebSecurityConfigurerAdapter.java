package com.psz.shoplist.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.psz.shoplist.web.CustomFilter;

@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport
public class CustomWebSecurityConfigurerAdapter  {

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;


	@Bean                                                             
	public UserDetailsService userDetailsService() throws Exception {
		// ensure the passwords are encoded properly
		UserBuilder users = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("user").password("password").roles("USER").build());
		manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
		return manager;
	}

	@Bean
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http
			.antMatcher("/api/**")                                   
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().hasRole("ADMIN")
			)
			.httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint);
        http.csrf().disable();
        http.addFilterBefore(new CustomFilter(),
            BasicAuthenticationFilter.class);            
		return http.build();
	}

}
