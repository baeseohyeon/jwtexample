package jwtexample.jwtexample.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDTO {

    private Long id;
    private String email;
    private String password;

    @Builder
    public UserDTO(Long id, String email, String password){
        this.id=id;
        this.email=email;
        this.password=password;
    }
}
