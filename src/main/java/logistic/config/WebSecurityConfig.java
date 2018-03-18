package logistic.config;

import logistic.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	   @Autowired
	   UserDetailsServiceImpl userDetailsService;
	
	   @Bean
	   public BCryptPasswordEncoder passwordEncoder() {
	      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	      return bCryptPasswordEncoder;
	   }
	   
	   @Autowired
	   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

	      // Setting Service to find User in the database.
	      // And Setting PassswordEncoder
	      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	   }
	   
	   @Override
	   protected void configure(HttpSecurity http) throws Exception {
		   
		   http.csrf().disable();
		   // Requires login with role ROLE_COUNTERPARTY or ROLE_MANAGER.
		   http.authorizeRequests().antMatchers("/accountInfo","/addContact","/addPlaceLoadUnload")
		   		.access("hasAnyRole('ROLE_COUNTERPARTY', 'ROLE_MANAGER')");		   
		   // Requires login with role ROLE_MANAGER.
//		   http.authorizeRequests().antMatchers(" ").access("hasRole('ROLE_MANAGER')");
		   http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		      // Configuration for Login Form.
		      http.authorizeRequests().and().formLogin()

		            .loginProcessingUrl("/j_spring_security_check") // Submit URL
		            .loginPage("/login")
		            .defaultSuccessUrl("/accountInfo")
		            .failureUrl("/login?error=true")
		            .usernameParameter("userName")
		            .passwordParameter("password")

		            // Configuration for the Logout page.
		            // (After logout, go to home page)
		            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");		   
	   }
}
