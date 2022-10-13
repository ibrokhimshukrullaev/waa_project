package edu.miu.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {
    private Integer id;
    private Integer jobAdvertisementId;
    private String studentUsername;
}
