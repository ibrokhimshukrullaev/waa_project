package edu.miu.backend.dto.responseDto;

import edu.miu.backend.dto.requestDto.AddressDto;
import edu.miu.backend.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponseDto {
    private Integer id;
    private Boolean active;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private AddressDto address;
    private Department department;
}
