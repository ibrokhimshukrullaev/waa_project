package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import edu.miu.backend.dto.requestDto.JobAdvertisementDto;
import edu.miu.backend.dto.requestDto.JobAdvertisementFilterQuery;
import edu.miu.backend.dto.responseDto.JobAdsAndAppsCountByAddressResponse;
import edu.miu.backend.dto.responseDto.JobAdsCountByTagResponse;
import edu.miu.backend.dto.responseDto.JobAdvertisementResponseAppliedDto;
import edu.miu.backend.dto.responseDto.JobAdvertisementResponseDto;
import edu.miu.backend.entity.*;
import edu.miu.backend.enums.S3BucketName;
import edu.miu.backend.repository.*;
import edu.miu.backend.service.JobAdvertisementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobAdvertisementServiceImpl implements JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepo;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepo;
    private final TagRepository tagRepo;
    private final JobApplicationRepository jobApplicationRepo;
    private final AmazonS3 amazonS3Client;

    private String AWS_URL = String.format("https://%s.s3.amazonaws.com/",
            S3BucketName.JOB_ADVERTISEMENT_BUCKET.getS3BucketName());

    @Override
    public List<JobAdvertisementResponseDto> findAll() {
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepo.findAll();
        return jobAdvertisements
                .stream()
                .map(jobAdvertisement -> {
                    JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper.map(jobAdvertisement,
                            JobAdvertisementResponseDto.class);
                    jobAdvertisementResponseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getFirstName() + " "
                            + jobAdvertisement.getCreatedBy().getLastName());
                    return jobAdvertisementResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public JobAdvertisementResponseDto findById(int id) {
        JobAdvertisement jobAdvertisement = jobAdvertisementRepo.findById(id).orElse(null);
        if (jobAdvertisement == null)
            return null;
        JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper
                .map(jobAdvertisementRepo.findById(id).get(), JobAdvertisementResponseDto.class);
        jobAdvertisementResponseDto.setCreatedBy(
                jobAdvertisement.getCreatedBy().getFirstName() + " " + jobAdvertisement.getCreatedBy().getLastName());
        return jobAdvertisementResponseDto;
    }

    @Override
    public JobAdvertisementResponseDto save(JobAdvertisementDto jobAdvertisementDto) throws Exception {
        JobAdvertisement jobAdvertisement = modelMapper.map(jobAdvertisementDto, JobAdvertisement.class);
        Student student = studentRepo.findByUsername(jobAdvertisementDto.getCreatedBy())
                .orElseThrow(() -> new Exception("Student not found!"));
        jobAdvertisement.setCreatedBy(student);
        var tagList = jobAdvertisementDto.getTagIds()
                .stream()
                .map(id -> tagRepo.findById(id).get())
                .collect(Collectors.toList());

        jobAdvertisement.setTags(tagList);
        if (jobAdvertisementDto.getFile() != null) {
            String url = uploadFileToAWSAndGetUrl(jobAdvertisementDto.getFile());
            System.out.println(url);
            jobAdvertisement.setFile(url);
        }
        JobAdvertisementResponseDto responseDto = modelMapper.map(jobAdvertisementRepo.save(jobAdvertisement),
                JobAdvertisementResponseDto.class);
        responseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getUsername());
        return responseDto;
    }

    @Override
    public void delete(int id) {
        jobAdvertisementRepo.deleteById(id);
    }

    @Override
    public JobAdvertisementDto update(JobAdvertisementDto jobAdvertisementDto, int id) throws Exception {
        JobAdvertisement jobAdvertisement = jobAdvertisementRepo.findById(id)
                .orElseThrow(() -> new Exception("Job Advertisement not found"));

        var tagList = jobAdvertisementDto.getTagIds()
                .stream()
                .map(tagId -> tagRepo.findById(tagId).get())
                .collect(Collectors.toList());

        jobAdvertisement.setBenefits(jobAdvertisementDto.getBenefits());
        jobAdvertisement.setDescription(jobAdvertisementDto.getDescription());
        if (jobAdvertisementDto.getFile() != null) {
            String url = uploadFileToAWSAndGetUrl(jobAdvertisementDto.getFile());
            jobAdvertisement.setFile(url);
        }
        jobAdvertisement.setTags(tagList);

        return jobAdvertisementDto;
    }

    @Override
    public List<JobAdvertisementResponseDto> findByUsername(String username) {
        Optional<Student> student = studentRepo.findByUsername(username);
        List<JobAdvertisement> jobAdvertisements = null;
        if (student.isPresent()) {
            jobAdvertisements = jobAdvertisementRepo.findByCreatedBy(student.get());
        } else {
            return null;
        }

        return jobAdvertisements.stream()
                .map(jobAdvertisement -> {
                    JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper.map(jobAdvertisement,
                            JobAdvertisementResponseDto.class);
                    jobAdvertisementResponseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getFirstName() + " "
                            + jobAdvertisement.getCreatedBy().getLastName());
                    return jobAdvertisementResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<JobAdvertisementResponseDto> findAllByUsernameExcluded(String username) {

        Student student = studentRepo.findByUsername(username).orElse(null);
        List<JobAdvertisement> jobAdvertisements = null;
        if (student != null) {
            jobAdvertisements = jobAdvertisementRepo.findByCreatedByNot(student);
        } else {
            return null;
        }

        return jobAdvertisements.stream()
                .map(jobAdvertisement -> {
                    JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper.map(jobAdvertisement,
                            JobAdvertisementResponseDto.class);
                    jobAdvertisementResponseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getFirstName() + " "
                            + jobAdvertisement.getCreatedBy().getLastName());
                    return jobAdvertisementResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<JobAdvertisementResponseAppliedDto> findByIdApplied(int id) {
        Optional<JobAdvertisement> jobAdvertisement = jobAdvertisementRepo.findById(id);
        if (!jobAdvertisement.isPresent())
            return null;

        List<JobApplication> jobApplications = jobApplicationRepo.findByJobAdvertisement(jobAdvertisement.get())
                .orElse(null);
        if (jobApplications == null)
            return null;

        List<JobAdvertisementResponseAppliedDto> collect = jobApplications.stream().map(jobApplication -> {
            JobAdvertisementResponseAppliedDto jobAdvertisementResponseAppliedDto = new JobAdvertisementResponseAppliedDto();
            jobAdvertisementResponseAppliedDto.setUsername(jobApplication.getStudent().getUsername());
            jobAdvertisementResponseAppliedDto.setCv(jobApplication.getStudent().getCv());
            return jobAdvertisementResponseAppliedDto;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<JobAdvertisementResponseDto> findRecentAll() {
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepo.findAll(Sort.by("createdAt").descending());
        return jobAdvertisements
                .stream()
                .map(jobAdvertisement -> {
                    JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper.map(jobAdvertisement,
                            JobAdvertisementResponseDto.class);
                    jobAdvertisementResponseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getFirstName() + " "
                            + jobAdvertisement.getCreatedBy().getLastName());
                    return jobAdvertisementResponseDto;
                }).limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobAdvertisementResponseDto> findTopAll() {
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepo.findTopAll();

        return jobAdvertisements
                .stream()
                .map(jobAdvertisement -> {
                    JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper.map(jobAdvertisement,
                            JobAdvertisementResponseDto.class);
                    jobAdvertisementResponseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getFirstName() + " "
                            + jobAdvertisement.getCreatedBy().getLastName());
                    return jobAdvertisementResponseDto;
                }).limit(10).collect(Collectors.toList());
    }

    @Override
    public List<JobAdvertisementResponseDto> filterAllByQuery(JobAdvertisementFilterQuery jobAdvertisementFilterQuery) {
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepo.findAll();
        String city = jobAdvertisementFilterQuery.getCity();
        String state = jobAdvertisementFilterQuery.getState();
        String companyName = jobAdvertisementFilterQuery.getCompanyName();
        List<Integer> tags = jobAdvertisementFilterQuery.getTags();

        if (city != null) {
            jobAdvertisements = filterJobAdvertisementsByCity(jobAdvertisements, city);
        }

        if (state != null) {
            jobAdvertisements = filterJobAdvertisementsByState(jobAdvertisements, state);
        }

        if (companyName != null) {
            jobAdvertisements = filterJobAdvertisementsByCompanyName(jobAdvertisements, companyName);
        }

        if (tags.size() > 0) {
            jobAdvertisements = filterJobAdvertisementsByTags(jobAdvertisements, tags);
        }
        return jobAdvertisements
                .stream()
                .map(jobAdvertisement -> {
                    JobAdvertisementResponseDto jobAdvertisementResponseDto = modelMapper.map(jobAdvertisement,
                            JobAdvertisementResponseDto.class);
                    jobAdvertisementResponseDto.setCreatedBy(jobAdvertisement.getCreatedBy().getFirstName() + " "
                            + jobAdvertisement.getCreatedBy().getLastName());
                    return jobAdvertisementResponseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<JobAdsAndAppsCountByAddressResponse> findJobAdsAndJobAppsCountByAddress() {
        HashMap<String, Integer> jobAdsCountMap = new HashMap<>();
        HashMap<String, Integer> jobAppsCountMap = new HashMap<>();

        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepo.findAll();
        List<JobApplication> jobApplications = jobApplicationRepo.findAll();

        for (JobAdvertisement jobAdvertisement : jobAdvertisements) {
            String address = jobAdvertisement.getState() + ", " + jobAdvertisement.getCity();
            jobAdsCountMap.put(address, jobAdsCountMap.getOrDefault(address, 0) + 1);
        }

        for (JobApplication jobApplication : jobApplications) {
            String address = jobApplication.getJobAdvertisement().getState() + ", "
                    + jobApplication.getJobAdvertisement().getCity();
            jobAppsCountMap.put(address, jobAppsCountMap.getOrDefault(address, 0) + 1);
        }

        List<JobAdsAndAppsCountByAddressResponse> result = new ArrayList<>();

        for (String address : jobAdsCountMap.keySet()) {
            result.add(new JobAdsAndAppsCountByAddressResponse(address, jobAdsCountMap.get(address),
                    jobAppsCountMap.getOrDefault(address, 0)));
        }

        return result;
    }

    @Override
    public List<JobAdsCountByTagResponse> findJobAdsCountByTag() {
        List<JobAdvertisement> jobAdvertisements = jobAdvertisementRepo.findAll();
        HashMap<String, Integer> jobAdvertisementsByTagMap = new HashMap<>();

        for (JobAdvertisement jobAdvertisement : jobAdvertisements) {
            if (jobAdvertisement.getTags().size() > 0) {
                for (Tag tag : jobAdvertisement.getTags()) {
                    String tagName = tag.getName();
                    jobAdvertisementsByTagMap.put(tagName, jobAdvertisementsByTagMap.getOrDefault(tagName, 0) + 1);
                }
            }
        }

        List<JobAdsCountByTagResponse> result = new ArrayList<>();
        for (String tag : jobAdvertisementsByTagMap.keySet()) {
            result.add(new JobAdsCountByTagResponse(tag, jobAdvertisementsByTagMap.get(tag)));
        }

        return result;
    }

    private List<JobAdvertisement> filterJobAdvertisementsByCity(List<JobAdvertisement> jobAdvertisements,
            String city) {
        return jobAdvertisements.stream()
                .filter(jobAdvertisement -> jobAdvertisement.getCity().contains(city))
                .collect(Collectors.toList());
    }

    private List<JobAdvertisement> filterJobAdvertisementsByState(List<JobAdvertisement> jobAdvertisements,
            String state) {
        return jobAdvertisements.stream()
                .filter(jobAdvertisement -> jobAdvertisement.getState().contains(state))
                .collect(Collectors.toList());
    }

    private List<JobAdvertisement> filterJobAdvertisementsByCompanyName(List<JobAdvertisement> jobAdvertisements,
            String companyName) {
        return jobAdvertisements.stream()
                .filter(jobAdvertisement -> jobAdvertisement.getCompanyName().contains(companyName))
                .collect(Collectors.toList());
    }

    private List<JobAdvertisement> filterJobAdvertisementsByTags(List<JobAdvertisement> jobAdvertisements,
            List<Integer> tags) {
        return jobAdvertisements.stream()
                .filter(jobAdvertisement -> {
                    List<Integer> tagIds = jobAdvertisement.getTags().stream().map(tag -> tag.getId())
                            .collect(Collectors.toList());
                    return !Collections.disjoint(tagIds, tags);
                })
                .collect(Collectors.toList());
    }

    private String uploadFileToAWSAndGetUrl(MultipartFile multipartFile) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        String keyName = new Date().getTime() + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
        ;
        try {
            amazonS3Client
                    .putObject(S3BucketName.JOB_ADVERTISEMENT_BUCKET.getS3BucketName(), keyName,
                            multipartFile.getInputStream(),
                            metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AWS_URL.concat(keyName);
    }
}
