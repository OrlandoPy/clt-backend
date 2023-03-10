package com.cltbackend.security;

import com.cltbackend.filter.CustomAuthenticationFilter;
import com.cltbackend.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.tokenExpiration.minutes}")
    private Long jwtTokenExpiration;

    @Value("${jwt.refreshTokenExpiration.minutes}")
    private Long refreshTokenExpiration;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.authorizeRequests().antMatchers("/usuario/**").hasAuthority("ROL_USER");
        http.authorizeRequests().antMatchers("/usuario/crearUsuario", "/auth/refreshToken", "/swagger-resources/**",
                "/swagger-ui/**", "/v2/api-docs/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), jwtSecret, jwtTokenExpiration, refreshTokenExpiration));
        http.addFilterBefore(new CustomAuthorizationFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
}
