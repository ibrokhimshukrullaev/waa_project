package edu.miu.backend.service;


import edu.miu.backend.dto.requestDto.JobApplicationDto;
import edu.miu.backend.dto.responseDto.JobApplicationResponseDto;

import java.util.List;

public interface JobApplicationService {

    List<JobApplicationResponseDto> findAll();

    JobApplicationResponseDto findById(int id);

    JobApplicationResponseDto save(JobApplicationDto jobApplicationDto) throws Exception;

    void delete(int id);

    JobApplicationResponseDto update(JobApplicationDto jobApplicationDto, int id) throws Exception;

    List<JobApplicationResponseDto> findByUsername(String username) throws Exception;
}
