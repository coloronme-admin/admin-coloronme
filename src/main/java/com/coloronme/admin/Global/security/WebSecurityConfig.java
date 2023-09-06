package com.coloronme.admin.Global.security;

import com.coloronme.admin.Global.jwt.JwtAuthFilter;
import com.coloronme.admin.Global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
//    private final CorsFilter corsFilter;

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/h2-console/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/configuration/ui",
                        "/configuration/security",
                        "/webjars/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
        http.csrf((csrf) -> csrf.disable());

                http
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/")
                        .permitAll()
                )
                .authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}




// 이전 security 참고


        // jwt사용시 필요없음 STATELESS 사용자 정보를 가지고 있지 않음
        // STATEFUL 은 사용자 정보를 DB에 저장을함
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.authorizeRequests()
//
//                .requestMatchers(HttpMethod.POST, "/api/~~").authenticated()
//                .requestMatchers(HttpMethod.POST, "/api/~~").authenticated()
//                .requestMatchers(HttpMethod.GET, "/api/~~").authenticated()
//                .requestMatchers(HttpMethod.DELETE, "/api/~~").authenticated()
//                .requestMatchers(HttpMethod.GET, "/api/~~").authenticated()
//                .requestMatchers(HttpMethod.PUT, "/api/~~").authenticated()
//                // .antMatchers(HttpMethod.GET, "/api//mountain/{mountainId}").access("hasRole('ROLE_USER')")
//
//                .anyRequest().permitAll()
//                .and();
////                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
