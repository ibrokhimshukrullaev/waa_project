package edu.miu.backend.service;

import edu.miu.backend.dto.requestDto.JobAdvertisementDto;
import edu.miu.backend.dto.requestDto.JobAdvertisementFilterQuery;
import edu.miu.backend.dto.responseDto.JobAdsAndAppsCountByAddressResponse;
import edu.miu.backend.dto.responseDto.JobAdsCountByTagResponse;
import edu.miu.backend.dto.responseDto.JobAdvertisementResponseAppliedDto;
import edu.miu.backend.dto.responseDto.JobAdvertisementResponseDto;

import java.util.List;

public interface JobAdvertisementService {
    List<JobAdvertisementResponseDto> findAll();

    JobAdvertisementResponseDto findById(int id);

    JobAdvertisementResponseDto save(JobAdvertisementDto jobAdDto) throws Exception;

    void delete(int id);

    JobAdvertisementDto update(JobAdvertisementDto jobAdDto, int id) throws Exception;

    List<JobAdvertisementResponseDto> findByUsername(String username);

    List<JobAdvertisementResponseDto> findAllByUsernameExcluded(String username);

    List<JobAdvertisementResponseAppliedDto> findByIdApplied(int id);

    List<JobAdvertisementResponseDto> findRecentAll();

    List<JobAdvertisementResponseDto> findTopAll();

    List<JobAdvertisementResponseDto> filterAllByQuery(JobAdvertisementFilterQuery jobAdvertisementFilterQuery);

    List<JobAdsAndAppsCountByAddressResponse> findJobAdsAndJobAppsCountByAddress();

    List<JobAdsCountByTagResponse> findJobAdsCountByTag();
}
