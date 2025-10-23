package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.request.AuthRequest;
import com.example.demo.utils.UserCustom;
import com.example.demo.table.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Users> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException(username);
            }
            return UserCustom.build(user.get());
    }

    public Users register(AuthRequest req) throws Exception {
        Optional<Users> user = userRepository.findByUsername(req.getUsername());
        if (user.isPresent()) {
            throw new Exception("ton tai");
        }

        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new Exception("Mật khẩu không trùng khớp!");
        }
        Users createItem = new Users();
        BeanUtils.copyProperties(req, createItem, "id" );
        createItem.setPassword(passwordEncoder.encode(createItem.getPassword()));
        userRepository.save(createItem);
        return createItem;
    }
}
