package com.commerce.web.config;

import com.commerce.web.security.jwt.JwtConfigurer;
import com.commerce.web.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    /*
         Special endpoints of restriction
    */
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String REGISTER_ENDPOINT = "/api/v1/auth/register";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean ();
    }

    protected void configure( HttpSecurity http ) throws Exception {

        http
                .httpBasic ().disable ()
                .csrf ().disable () // Disable CSRF
                .sessionManagement ().sessionCreationPolicy ( SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests ()
                .antMatchers ( LOGIN_ENDPOINT ).permitAll () // Login endpoint is available for all
                .antMatchers ( REGISTER_ENDPOINT ).permitAll () // As well as register one
                .antMatchers ( ADMIN_ENDPOINT).hasRole ( "ADMIN" ) // Admin endpoints require role of Admin!
                .anyRequest ().authenticated () // Other ones require basic authentication
                .and ()
                .apply ( new JwtConfigurer ( jwtTokenProvider)); // Apply custom JWT filte/r


    }

}
