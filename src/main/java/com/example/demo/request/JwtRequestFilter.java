package com.example.demo.request;

import com.example.demo.service.MyUserDetailsService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Lấy header 'Authorization' từ request
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Kiểm tra header có tồn tại và có bắt đầu bằng "Bearer " không
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Lấy phần token (bỏ "Bearer ")
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Xử lý nếu token hết hạn hoặc không hợp lệ
                logger.warn("JWT Token is invalid or expired");
            }
        }

        // 3. Nếu lấy được username và CHƯA CÓ xác thực trong SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Tải thông tin user từ service
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Nếu token hợp lệ, thiết lập xác thực cho Spring Security
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Tạo đối tượng xác thực
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt thông tin xác thực vào SecurityContext
                // Từ đây, Spring Security sẽ coi user này đã được xác thực
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 5. Chuyển request/response cho filter tiếp theo trong chuỗi
        filterChain.doFilter(request, response);
    }


}
