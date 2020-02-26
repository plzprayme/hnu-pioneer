package com.hnu.pioneer.config;

import com.hnu.pioneer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
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
    protected void configure(HttpSecurity http) throws  Exception {
        http
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/create-study", "/create-study/save").hasAnyRole("ADMIN","LEADER")
                    .antMatchers("/signup/**","/**", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2-console/**", "/signup/request", "/save/request")
//                    .disable()
                .and()
                    .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/signin/request")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/auth")
                    .permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/");

    }
}
