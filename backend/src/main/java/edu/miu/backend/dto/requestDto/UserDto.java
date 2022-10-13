package edu.miu.backend.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
}
