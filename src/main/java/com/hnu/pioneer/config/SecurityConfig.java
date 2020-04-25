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
                    .ignoringAntMatchers("/h2-console/**", "/signup/request", "/save/request", "/create-study/save", "/update-study/**", "/change-password/request**")
                    .and()
                .authorizeRequests()
                    .antMatchers("/admin**").hasRole("ADMIN")
                    .antMatchers("/create-study", "/create-study/save").hasAnyRole("LEADER", "ADMIN")
                    .antMatchers("/mystudy", "/study/register**").hasAnyRole("STUDENT", "LEADER","ADMIN")
                    .antMatchers("/signup/**", "/**", "/create-study").permitAll()
                    .antMatchers("/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/signin/request")
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
