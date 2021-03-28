package jwtexample.jwtexample.api;


import jwtexample.jwtexample.config.JwtTokenProvider;
import jwtexample.jwtexample.dto.UserDTO;
import jwtexample.jwtexample.entity.User;
import jwtexample.jwtexample.repository.UserRepository;
import jwtexample.jwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public Long join(@RequestBody UserDTO userDTO){
        Long userId = userService.save(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
        return userId;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO){
        if(userService.confirmUser(userDTO)){
            User user = userService.findOne(userDTO.getEmail());
            return jwtTokenProvider.createToken(user.getEmail(),user.getRoles());
        }else{
            return "failed";
        }
    }

    @GetMapping("/user/myinfo")
    public User myInfo(Principal principal){
        String name = principal.getName();
        User user = userService.findOne(name);
        return user;
    }



}
