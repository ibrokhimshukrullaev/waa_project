package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.JobApplicationDto;
import edu.miu.backend.dto.responseDto.JobApplicationResponseDto;
import edu.miu.backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-applications")
@CrossOrigin("*")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @GetMapping("")
    public ResponseEntity<List<JobApplicationResponseDto>> getAll() {
        return ResponseEntity.ok().body(jobApplicationService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<JobApplicationResponseDto>> getByUsername(@PathVariable String username)
            throws Exception {
        return ResponseEntity.ok().body(jobApplicationService.findByUsername(username));
    }

    @PostMapping(path = "")
    public ResponseEntity<JobApplicationResponseDto> createJobApplication(@RequestBody JobApplicationDto jobApplicationDto)
            throws Exception {
        return ResponseEntity.ok().body(jobApplicationService.save(jobApplicationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDto> updateJobApplication(@RequestBody JobApplicationDto jobApplicationDto,
            @PathVariable int id) throws Exception {
        return ResponseEntity.ok().body(jobApplicationService.update(jobApplicationDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteJobAd(@PathVariable int id) {
        jobApplicationService.delete(id);
    }
}
