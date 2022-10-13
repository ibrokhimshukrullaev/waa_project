package edu.miu.backend.dto.responseDto;

import edu.miu.backend.dto.requestDto.AddressDto;
import edu.miu.backend.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private Integer id;
    private String username;
    private Boolean active;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
    private Department major;
    private Float gpa;
    private String cv;
}
