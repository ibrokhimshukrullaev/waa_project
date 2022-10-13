package edu.miu.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAdvertisementFilterQuery {
    private String state;
    private String city;
    private String companyName;
    private List<Integer> tags;
}
