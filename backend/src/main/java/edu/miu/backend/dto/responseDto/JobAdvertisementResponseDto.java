package edu.miu.backend.dto.responseDto;

import edu.miu.backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAdvertisementResponseDto {
    private Integer id;
    private String companyName;
    private String description;
    private String benefits;
    private String state;
    private String city;
    private String createdBy;
    private List<Tag> tags;
    private String file;
}
