
  package org.sid.sec;
  
  import java.util.ArrayList;
  import java.util.Collection; 

  
  import org.sid.entities.AppUser;
import org.sid.filtres.JWTAuthenticationFilter;
import org.sid.filtres.JWTAuthorizationFilter;
import org.sid.repository.AppUserRepository;
  import org.sid.service.AccountService; 
  import  org.springframework.beans.factory.annotation.Autowired;
  import  org.springframework.context.annotation.Bean; 
  import  org.springframework.context.annotation.Configuration;
import  org.springframework.security.authentication.AuthenticationManager; 
  import  org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
  import  org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
  import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; 
  import org.springframework.security.config.http.SessionCreationPolicy;
  import org.springframework.security.core.authority.SimpleGrantedAuthority;
  import  org.springframework.security.core.GrantedAuthority;
  import  org.springframework.security.core.userdetails.User; 
  import  org.springframework.security.core.userdetails.UserDetails;
  import  org.springframework.security.core.userdetails.UserDetailsService; 
  import  org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
  
  @Configuration
  @CrossOrigin("*")
  @EnableWebSecurity public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
	  @Autowired private AccountService accountService;
	  @Autowired private AppUserRepository appUserRepository;
   
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception { 

	  auth.userDetailsService(new UserDetailsService() {
		  
		  @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
			System.out.println("-----------------------");
			  System.out.println(email);
			  AppUser appUser = appUserRepository.findByEmail(email);
			  Collection<GrantedAuthority>  authorities= new ArrayList<>();
				/*
				 * appUser.getAppRoles().forEach(r->{ authorities.add(new
				 * SimpleGrantedAuthority(r.getRoleName())); });
				 */
			  return new  User(appUser.getEmail(), appUser.getPassword(), authorities);
			  } 
		  }); 
  }
  
  @Override protected void configure(HttpSecurity http) throws Exception {

	  http.csrf().disable();
	  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  http.headers().frameOptions().disable();
	  http.authorizeRequests().antMatchers("/","/Users/**","/Comments","/v2/api-docs","/Posts/**","/login","/refreshToken/**").permitAll();

	  http.authorizeRequests().anyRequest().authenticated();
	  http.addFilter(new JWTAuthenticationFilter(authenticationManagerBean(), appUserRepository));
	  http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	   
  }
  
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception { 
	  return super.authenticationManagerBean(); 
	  } 
  }
 