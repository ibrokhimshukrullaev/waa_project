package edu.miu.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
    private Integer departmentId;
}
