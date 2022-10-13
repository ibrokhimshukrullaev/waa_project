package edu.miu.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFilterQuery {
    private String state;
    private String city;
    private String major;
    private String name;
}
