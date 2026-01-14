package com.demo;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/list").hasAuthority("ADMINISTRATOR")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN") // /user endpoint to be accessible for authenticated users with the ROLE_USER or ROLE_ADMIN
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/public").hasAuthority("WRITE") //llows only users with the WRITE authority to make POST requests to the /public endpoint
                        .requestMatchers("/secured","/api/antifraud/**").authenticated() // The /secured URL is made accessible to any authenticated users
                        .requestMatchers(HttpMethod.GET, "/*").permitAll() // "/*" wildcard is used to define both the / and /public endpoints to allow any user, whether authenticated or not, to make GET requests to these endpoints
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin())) // make h2 console visible in the browser
                .csrf(AbstractHttpConfigurer::disable)  // for POST requests via Postman
//                .exceptionHandling(handing -> handing
//                        .authenticationEntryPoint(restAuthenticationEntryPoint) // Handles auth error
//                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        var user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("pass1"))
//                .authorities("WRITE")
//                .build();
//        var user2 = User.withUsername("user2")
//                .password(passwordEncoder().encode("pass2"))
//                .roles("USER")
//                .build();
//        var user3 = User.withUsername("user3")
//                .password(passwordEncoder().encode("pass3"))
//                .authorities("ROLE_ADMIN", "WRITE")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }
}
