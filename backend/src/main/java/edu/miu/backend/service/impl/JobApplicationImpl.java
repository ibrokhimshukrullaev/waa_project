package edu.miu.backend.service.impl;


import edu.miu.backend.dto.requestDto.JobApplicationDto;
import edu.miu.backend.dto.responseDto.JobApplicationResponseDto;
import edu.miu.backend.entity.JobAdvertisement;
import edu.miu.backend.entity.JobApplication;

import edu.miu.backend.entity.Student;
import edu.miu.backend.repository.JobAdvertisementRepository;
import edu.miu.backend.repository.JobApplicationRepository;
import edu.miu.backend.repository.StudentRepository;
import edu.miu.backend.service.JobApplicationService;
import edu.miu.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepo;
    private final JobAdvertisementRepository jobAdvertisementRepo;
    private final StudentRepository studentRepo;
    private final ModelMapper modelMapper;

    private final NotificationService notificationService;


    @Override
    public List<JobApplicationResponseDto> findAll() {

        List<JobApplication> jobApplications = jobApplicationRepo.findAll();

        return jobApplications
                .stream()
                .map(jobApplication -> {
                    JobApplicationResponseDto jobApplicationResponseDto = modelMapper.map(jobApplication, JobApplicationResponseDto.class);
                    jobApplicationResponseDto.setJobAdvertisementId(jobApplication.getJobAdvertisement().getId());
                    jobApplicationResponseDto.setStudentUsername(jobApplication.getStudent().getUsername());
                    return jobApplicationResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationResponseDto findById(int id) {
        JobApplication jobApplication = jobApplicationRepo.findById(id).orElse(null);
        JobApplicationResponseDto jobApplicationResponseDto = modelMapper.map(jobApplication, JobApplicationResponseDto.class);
        jobApplicationResponseDto.setJobAdvertisementId(jobApplication.getJobAdvertisement().getId());
        jobApplicationResponseDto.setStudentUsername(jobApplication.getStudent().getUsername());
        return jobApplicationResponseDto;
    }

    @Override
    public JobApplicationResponseDto save(JobApplicationDto jobApplicationDto) throws Exception {

        JobAdvertisement jobAdvertisement = jobAdvertisementRepo.findById(jobApplicationDto.getJobAdvertisementId())
                .orElseThrow(() -> new Exception("Job Advertisement not found"));

        Student student = studentRepo.findByUsername(jobApplicationDto.getStudentUsername())
                .orElseThrow(() -> new Exception("Student not found"));

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobAdvertisement(jobAdvertisement);
        jobApplication.setStudent(student);

        JobApplication existingJobApplication = jobApplicationRepo.findByJobAdvertisementAndStudent(jobAdvertisement, student).orElse(null);
        if(existingJobApplication != null) return null;

        notificationService.sendMessage(jobAdvertisement.getCreatedBy().getId());
        JobApplicationResponseDto savedJobApplicationDto = modelMapper.map(jobApplicationRepo.save(jobApplication), JobApplicationResponseDto.class);
        savedJobApplicationDto.setJobAdvertisementId(jobAdvertisement.getId());
        savedJobApplicationDto.setStudentUsername(student.getUsername());

        return savedJobApplicationDto;
    }

    @Override
    public void delete(int id) {
        jobApplicationRepo.deleteById(id);
    }

    @Override
    public JobApplicationResponseDto update(JobApplicationDto jobApplicationDto, int id) throws Exception {
        return null;
    }

    @Override
    public List<JobApplicationResponseDto> findByUsername(String username) throws Exception {

        Student student = studentRepo.findByUsername(username)
                .orElseThrow(() -> new Exception("Student not found"));

        List<JobApplication> jobApplications = jobApplicationRepo.findByStudent(student).orElse(null);
        if(jobApplications == null) return null;

        return jobApplications.stream()
                .map(jobApplication -> {
                    JobApplicationResponseDto jobApplicationResponseDto = modelMapper.map(jobApplication, JobApplicationResponseDto.class);
                    jobApplicationResponseDto.setJobAdvertisementId(jobApplication.getJobAdvertisement().getId());
                    jobApplicationResponseDto.setStudentUsername(jobApplication.getStudent().getUsername());
                    return jobApplicationResponseDto;
                }).collect(Collectors.toList());
    }

}
