package com.rudraksh.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rudraksh.springboot.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		
		//.anyRequest().authenticated()
	.antMatchers(
				 "/registration**","/saveTeacher/**","/saveStudent/**",
	                "/js/**",
	                "/css/**",
	                "/img/**"
	                ).permitAll()
	.antMatchers("/home/**",

			  "/updateTeacher/**",
			   "/updateStudent/**",
			   "/saveActivity/**",
			   "/showActivity/**",
			   "/showActivityForm/**",
			   "/showMyActivity/**",
			   "/accountSettingTeacher/**",
			   "/accountSettingStudent/**",
			   "/showFormForUpdate/**",
			   "/delete/**",
			   "/showFormForUpdateProfileTeacher/**",
			   "/showFormForUpdateProfileStudent/**",
			   "/showFormForUpdatePasswordTeacher/**",
			   "/updatePasswordTeacher/**",
			   "/showFormForUpdatePasswordStudent/**",
			   "/updatePasswordStudent/**").authenticated()
		.and()
		.formLogin()
		.loginPage("/")
		.defaultSuccessUrl("/home",true)
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/").deleteCookies("JSESSIONID").addLogoutHandler(
				new HeaderWriterLogoutHandler(
						new ClearSiteDataHeaderWriter(
								ClearSiteDataHeaderWriter.Directive.CACHE,
								ClearSiteDataHeaderWriter.Directive.COOKIES,
								ClearSiteDataHeaderWriter.Directive.STORAGE
						)
				)
		)
		
		.permitAll();
		http.cors().disable();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
