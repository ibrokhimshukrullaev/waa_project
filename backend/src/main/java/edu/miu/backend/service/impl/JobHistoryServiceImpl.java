package edu.miu.backend.service.impl;

import edu.miu.backend.dto.requestDto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;
import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repository.JobHistoryRepository;
import edu.miu.backend.repository.StudentRepository;
import edu.miu.backend.repository.TagRepository;
import edu.miu.backend.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepository jobHistoryRepo;
    private final StudentRepository studentRepo;
    private final TagRepository tagRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<JobHistoryResponseDto> findAll() {
        List<JobHistory> histories = jobHistoryRepo.findAll();
        List<JobHistoryResponseDto> collect = histories
                .stream()
                .map(jobHistory -> {
                    JobHistoryResponseDto jobHistoryResponseDto = modelMapper.map(jobHistory,
                            JobHistoryResponseDto.class);
                    return jobHistoryResponseDto;
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<JobHistoryResponseDto> findById(int id) throws Exception {
        Student student = studentRepo.findById(id).orElseThrow(() -> new Exception("Student not found"));
        return jobHistoryRepo.findByStudent(student)
                .stream().map(jobHistory -> modelMapper.map(jobHistory, JobHistoryResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        jobHistoryRepo.deleteById(id);
    }

    @Override
    public JobHistoryResponseDto update(JobHistoryDto jobHistoryDto, String username) {
        // TODO:
        return null;
    }

    @Override
    public JobHistoryResponseDto save(JobHistoryDto jobHistoryDto, String username) throws Exception {

        Student student = studentRepo.findByUsername(username).orElseThrow(() -> new Exception("Student not found"));
        JobHistory jobHistory = modelMapper.map(jobHistoryDto, JobHistory.class);

        var tagList = jobHistoryDto.getTagIds()
                .stream()
                .map(tagId -> tagRepo.findById(tagId).get())
                .collect(Collectors.toList());

        jobHistory.setTags(tagList);
        jobHistory.setStudent(student);
        JobHistory newJobHistory = jobHistoryRepo.save(jobHistory);

        return modelMapper.map(newJobHistory, JobHistoryResponseDto.class);
    }

    @Override
    public List<JobHistoryResponseDto> findByUsername(String username) throws Exception {
        Student student = studentRepo.findByUsername(username).orElseThrow(() -> new Exception("Student not found"));
        List<JobHistoryResponseDto> jobHistories = jobHistoryRepo.findByStudent(student).stream()
                .map(jobHistory -> {
                    return modelMapper.map(jobHistory, JobHistoryResponseDto.class);
                })
                .collect(Collectors.toList());
        return jobHistories;
    }
}
