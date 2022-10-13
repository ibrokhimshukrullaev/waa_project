package edu.miu.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAdvertisementDto {
    private Integer id;
    private String companyName;
    private String description;
    private String benefits;
    private String state;
    private String city;
    private String createdBy;
    private List<Integer> tagIds;
    private MultipartFile file;
}