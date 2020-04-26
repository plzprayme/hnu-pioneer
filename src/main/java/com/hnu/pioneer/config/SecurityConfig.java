package com.hnu.pioneer.config;

import com.hnu.pioneer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .csrf()
                    .ignoringAntMatchers("/h2-console/**",
                            "/api/v1/studies", "/api/v1/studies/**", "/api/v1/members", "/api/v1/members/**/password",
                            "/api/v1/members/register/studies/**", "/api/v1/members/unregister/studies/**")
                    .and()
                .authorizeRequests()
                    .antMatchers("/admin**").hasRole("ADMIN")
                    .antMatchers("/studies/save", "/studies/**/update").hasAnyRole("LEADER", "ADMIN") // LEADER ADMIN VIEW
                    .antMatchers( "/api/v1/studies", "/api/v1/studies/**").hasAnyRole("LEADER", "ADMIN") // LEADER ADMIN API
                    .antMatchers("/studies/own", "/studies/**").hasAnyRole("STUDENT", "LEADER", "ADMIN") // Members VIEW
                    .antMatchers("/api/v1/members/unregister/studies/**", "/api/v1/members/register/studies/**").hasAnyRole("STUDENT", "LEADER","ADMIN") // Members API
//                    .antMatchers( "/", "/howtogroom", "/signin", "/signup", "/findid", "/findpassword", "/changepassword/**", "/studies").permitAll() // Permit All View
                    .antMatchers( "/**").permitAll()
                    .antMatchers("/api/v1/members").permitAll()
                    .antMatchers("/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/signin")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/");
    }
}
