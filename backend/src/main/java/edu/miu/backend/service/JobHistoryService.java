package edu.miu.backend.service;

import edu.miu.backend.dto.requestDto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;

import java.util.List;

public interface JobHistoryService {
    List<JobHistoryResponseDto> findAll();

    List<JobHistoryResponseDto> findById(int id) throws Exception;

    void delete(int id);

    JobHistoryResponseDto update(JobHistoryDto jobHistoryDto, String username);

    JobHistoryResponseDto save(JobHistoryDto jobHistoryDto, String username) throws Exception;

    List<JobHistoryResponseDto> findByUsername(String username) throws Exception;
}
