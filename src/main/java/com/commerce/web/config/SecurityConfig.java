package com.commerce.web.config;

import com.commerce.web.constants.UserRolesConstants;
import com.commerce.web.security.jwt.JwtConfigurer;
import com.commerce.web.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

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
    private static final String VERIFY_USER_ENDPOINT = "/api/v1/auth/verify/**";
    private static final String RESENT_VERIFY_USER_ENDPOINT = "/api/v1/auth/resend/**";
    private static final String FORGOT_PASSWORD_ENDPOINT = "/api/v1/auth/forgotPassword";
    private static final String RESET_PASSWORD_ENDPOINT = "/api/v1/auth/resetPassword";


    @Autowired
    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider
    ) {
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
                .cors ().and ()
                .sessionManagement ().sessionCreationPolicy ( SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests ()
                /*
                    Such endpoints as: login,register,verify and resend verification are permitted to all
                 */
                .antMatchers ( LOGIN_ENDPOINT ).permitAll ()
                .antMatchers ( REGISTER_ENDPOINT ).permitAll ()
                .antMatchers ( VERIFY_USER_ENDPOINT ).permitAll ()
                .antMatchers ( RESENT_VERIFY_USER_ENDPOINT ).permitAll ()
                .antMatchers ( RESET_PASSWORD_ENDPOINT ).permitAll ()
                .antMatchers ( FORGOT_PASSWORD_ENDPOINT ).permitAll ()
                .antMatchers ( ADMIN_ENDPOINT).hasRole ( UserRolesConstants.ADMIN ) // Admin endpoints require role of Admin!
                .anyRequest ().authenticated () // Other ones require basic authentication
                .and ()
                .apply ( new JwtConfigurer ( jwtTokenProvider)); // Apply custom JWT filter

    }

}
