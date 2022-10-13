package edu.miu.backend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAdsAndAppsCountByAddressResponse {
    private String address;
    private Integer jobAdsCount;
    private Integer jobAppsCount;
}
