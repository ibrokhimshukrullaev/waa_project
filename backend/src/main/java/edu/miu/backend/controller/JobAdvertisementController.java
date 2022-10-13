package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.JobAdvertisementDto;
import edu.miu.backend.dto.requestDto.JobAdvertisementFilterQuery;
import edu.miu.backend.dto.responseDto.JobAdsAndAppsCountByAddressResponse;
import edu.miu.backend.dto.responseDto.JobAdsCountByTagResponse;
import edu.miu.backend.dto.responseDto.JobAdvertisementResponseAppliedDto;
import edu.miu.backend.dto.responseDto.JobAdvertisementResponseDto;
import edu.miu.backend.service.JobAdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-advertisements")
@CrossOrigin("*")
public class JobAdvertisementController {
    private final JobAdvertisementService jobAdvertisementService;

    @GetMapping("")
    public ResponseEntity<List<JobAdvertisementResponseDto>> getAll() {
        return ResponseEntity.ok().body(jobAdvertisementService.findAll());
    }

    @GetMapping("/filter")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobAdvertisementResponseDto>> filterAllByQuery(
            @RequestParam(required = false) String companyName, @RequestParam(required = false) String state,
            @RequestParam(required = false) String city, @RequestParam(required = false) List<Integer> tags) {
        System.out.println(tags);
        return ResponseEntity.ok().body(jobAdvertisementService
                .filterAllByQuery(new JobAdvertisementFilterQuery(state, city, companyName, tags)));
    }

    @GetMapping("/recent")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobAdvertisementResponseDto>> getRecentAll() {
        return ResponseEntity.ok().body(jobAdvertisementService.findRecentAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<List<JobAdvertisementResponseAppliedDto>> getByIdApplied(@PathVariable int id) {
        return ResponseEntity.ok().body(jobAdvertisementService.findByIdApplied(id));
    }

    @GetMapping("/{username}")
    @RolesAllowed({ "student", "admin" })
    public ResponseEntity<List<JobAdvertisementResponseDto>> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(jobAdvertisementService.findByUsername(username));
    }

    @GetMapping("/top")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobAdvertisementResponseDto>> getTop() {
        return ResponseEntity.ok().body(jobAdvertisementService.findTopAll());
    }

    @GetMapping("/all/{username}")
    @RolesAllowed("student")
    public ResponseEntity<List<JobAdvertisementResponseDto>> getByUsernameExcluded(@PathVariable String username) {
        return ResponseEntity.ok().body(jobAdvertisementService.findAllByUsernameExcluded(username));
    }

    @PostMapping(path = "")
    @RolesAllowed("student")
    public ResponseEntity<JobAdvertisementResponseDto> addJobAdvertisement(
            @ModelAttribute JobAdvertisementDto jobAdvertisementDto) throws Exception {
        return ResponseEntity.ok().body(jobAdvertisementService.save(jobAdvertisementDto));
    }

    @PutMapping("/{id}")
    @RolesAllowed("student")
    public ResponseEntity<JobAdvertisementDto> updateJobAdvertisement(
            @RequestBody JobAdvertisementDto jobAdvertisementDto, @PathVariable int id) throws Exception {
        return ResponseEntity.ok().body(jobAdvertisementService.update(jobAdvertisementDto, id));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("student")
    public void deleteJobAdvertisement(@PathVariable int id) {
        jobAdvertisementService.delete(id);
    }

    @GetMapping("/count-by-address")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobAdsAndAppsCountByAddressResponse>> countJobAdsAndAppsByAddress() {
        return ResponseEntity.ok().body(jobAdvertisementService.findJobAdsAndJobAppsCountByAddress());
    }

    @GetMapping("/count-by-tag")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobAdsCountByTagResponse>> countJobAdsByTag() {
        return ResponseEntity.ok().body(jobAdvertisementService.findJobAdsCountByTag());
    }

}
