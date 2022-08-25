/**
 * 
 */
package com.spring.payapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author chun
 * @package payApp
 * @time 4:01:14 PM
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private PasswordEncoder encoder;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(encoder)
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enable from account where email = ?")
                .authoritiesByUsernameQuery("select email, role from account where email = ?");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/resources/**","/signUp","/login","/user/forgotPassword","/user/confirmKey","/user/createdNewPassword","/verificationMail/**")
                .permitAll()
                .antMatchers("/member/**").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/owner/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/doLogout", "GET"))
                .logoutSuccessUrl("/");

    }

}