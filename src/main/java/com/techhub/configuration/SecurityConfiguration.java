package com.techhub.configuration;

import com.techhub.repository.UserRepository;
import com.techhub.security.JwtAuthEntryPoint;
import com.techhub.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

//@EnableAutoConfiguration
@EnableWebSecurity
@ComponentScan(basePackageClasses = {
        UserDetailsServiceImpl.class,
        JwtAuthEntryPoint.class,
        UserRepository.class})
public class SecurityConfiguration {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthEntryPoint unauthorizedHandler;
    private final Filter filter;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService
            , JwtAuthEntryPoint unauthorizedHandler
            , @Qualifier("jwtAuthFilter") Filter filter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.filter = filter;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/*", corsConfig);
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .ignoringAntMatchers("/api/**");

        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                // configure not use session to save client info
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .antMatchers("/api/admin/**","/api/manager/products/**")
                .hasRole("ADMIN");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .clearAuthentication(true);

        http.authorizeHttpRequests().and().exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
                }))
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied!");
                }))
                .defaultAuthenticationEntryPointFor(
                        new HttpStatusEntryPoint(HttpStatus.BAD_REQUEST),
                        new AntPathRequestMatcher("/api/**"));

        // Configure remember me (save token in database)
        http.rememberMe()
                .key("uniqueAndSecretKey")
                .rememberMeParameter("remember")
                .rememberMeCookieName("rememberCookie")
                .tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(7 * 24 * 60 * 60);//7 days


        // Use JwtAuthorizationFilter to check token -> get user info
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }
}
