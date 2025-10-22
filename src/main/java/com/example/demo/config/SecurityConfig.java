package com.example.demo.config;

import com.example.demo.request.JwtRequestFilter;
import com.example.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Tắt CSRF (Cross-Site Request Forgery)
                // Vì chúng ta dùng JWT (stateless), không cần CSRF
                .csrf(csrf -> csrf.disable())

                // Cấu hình các quy tắc ủy quyền (authorization)
                .authorizeHttpRequests(auth -> auth
                        // Cho phép truy cập công khai đến endpoint /authenticate
                        .requestMatchers("/authenticate").permitAll()
                        // Tất cả các request khác đều yêu cầu xác thực
                        .anyRequest().authenticated()
                )

                // Cấu hình quản lý session
                .sessionManagement(session -> session
                        // Sử dụng stateless session
                        // Spring Security sẽ không tạo hoặc sử dụng session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Thêm filter JWT của chúng ta vào *trước* filter UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
