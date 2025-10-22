package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserCustom;
import com.example.demo.table.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user1".equals(username)) {
            // Mật khẩu là "password" (đã được mã hóa bằng BCrypt)
            // Bạn NÊN dùng BCryptPasswordEncoder().encode("password") để tạo chuỗi này
            return new User("user1",
                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", // Đây là "password"
                    new ArrayList<>()); // Danh sách quyền (roles)
        } else {
            Optional<Users> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException(username);
            }
            return UserCustom.build(user.get());
        }
    }
}
