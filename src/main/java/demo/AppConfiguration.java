package demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class AppConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
 
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
 
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,password, enabled from users where username=?")
		.authoritiesByUsernameQuery(
			"select username, role from user_roles where username=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/hello").permitAll()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().access("hasRole('ROLE_USER')")
		//.and()
		  //.formLogin().loginPage("/login").failureUrl("/login?error")
		  //.usernameParameter("username").passwordParameter("password").permitAll()
		//.and()
		  //.logout().logoutSuccessUrl("/login?logout")
		//.and()
		  //.exceptionHandling().accessDeniedPage("/403")
		.and()
		  .httpBasic();
	}
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/hubkicDB");
	    driverManagerDataSource.setUsername("hubkic");
	    driverManagerDataSource.setPassword("hubkic");
	    return driverManagerDataSource;
	}
}
