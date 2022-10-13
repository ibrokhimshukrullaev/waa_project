package edu.miu.backend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponseDto {
    private Integer id;
    private Integer jobAdvertisementId;
    private String studentUsername;
}
