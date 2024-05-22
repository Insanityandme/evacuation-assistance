package com.evac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.evac.security.jwt.AuthEntryPointJwt;
import com.evac.security.jwt.AuthTokenFilter;
import com.evac.security.services.UserDetailsServiceImpl;

/**
 * this class is a configuration class for Spring Security.
 * It defines how to configure authentication and authorization
 * for the web application.
 * It includes configuration for JWT authentication,
 * password encoding and filters.
 */
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

    /**
     * Defines the authentication JWT token filter bean.
     * @return the AuthTokenFilter bean.
     */
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

    /**
     * Defines the DaoAuthenticationProvider bean
     * @return DaoAuthenticationProvider bean
     */
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

    /**
     * Defines the AuthenticationManager using AuthenticationConfiguration object.
     * @param authConfig
     * @return AuthenticationManager bean.
     */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

    /**
     * @return passwordEncoder bean.
     */
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

    /**
     * Defines the securityFilterChain bean that defines how to
     * configure authentication and authorization for the
     * web application
     * also configures CORS, CSRF, session management.
     * @param http the HttpSecurity object
     * @return SecurityFilterChain bean.
     */
	@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers("/api/auth/**").permitAll()
        .antMatchers("/api/test/**").permitAll()
            .antMatchers("/api/evacAuth/**").permitAll()
            .antMatchers("/api/deputyAuth/**").permitAll()
            .antMatchers("/api/userAuth/**").permitAll()
            .antMatchers("/api/notification/**").permitAll()
            .antMatchers("/api/sensor/**").permitAll()
            .antMatchers("/api/alarm/**").permitAll()
        .anyRequest().authenticated();
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }




}
