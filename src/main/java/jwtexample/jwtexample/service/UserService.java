package jwtexample.jwtexample.service;

import jwtexample.jwtexample.dto.UserDTO;
import jwtexample.jwtexample.entity.User;
import jwtexample.jwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
    }

    public Long save(String email, String password) {
        return userRepository.save(User.builder()
                .email(email)
                .password(password)
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getId();
    }

    public User findOne(String email) {
        User user = userRepository.findByEmail(email).get();
        return user;
    }

    public boolean confirmUser(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        if(user.getPassword()==userDTO.getPassword()){
            return false;
        }else
            return true;
    }
}
