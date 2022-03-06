package com.bank.config;

import java.io.PrintWriter;

import com.bank.security.CustomAuthenticationFailureHandler;
import com.bank.security.CustomAuthenticationSuccessHandler;
import com.bank.security.ResourceOwnerAuthenticationFilter;
import com.bank.service.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/vendor/**", "/js/**", "/favicon*/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login*/**", "/signup", "/h2-console/**").permitAll()
                .antMatchers("/mypage", "/deposit", "/withdraw", "/transfer").hasRole("USER")
                .antMatchers("/list").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-cache");
                    PrintWriter writer = response.getWriter();
                    writer.println(new AccessDeniedException("access denied !"));
                })
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .logout().logoutSuccessUrl("/loginPage");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder((passwordEncoder()));

        return authenticationProvider;
    }

    @Bean
    public ResourceOwnerAuthenticationFilter authenticationFilter() throws Exception {
        ResourceOwnerAuthenticationFilter filter = new ResourceOwnerAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/login");
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");

        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());

        filter.afterPropertiesSet();

        return filter;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/loginPage");
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/");

        return successHandler;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        CustomAuthenticationFailureHandler failureHandler = new CustomAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/login?error=loginfali");

        return failureHandler;
    }

}
