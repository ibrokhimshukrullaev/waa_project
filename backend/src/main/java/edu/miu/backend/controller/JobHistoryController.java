package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.JobHistoryDto;
import edu.miu.backend.dto.responseDto.JobHistoryResponseDto;
import edu.miu.backend.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-histories")
@CrossOrigin("*")
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;

    @GetMapping("")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobHistoryResponseDto>> getAll() {
        return ResponseEntity.ok().body(jobHistoryService.findAll());
    }

    @GetMapping("/{username}")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<JobHistoryResponseDto>> getById(@PathVariable String username) throws Exception {
        return ResponseEntity.ok().body(jobHistoryService.findByUsername(username));
    }

    @PostMapping("/{username}")
    @RolesAllowed("student")
    public ResponseEntity<JobHistoryResponseDto> addJobHistory(@RequestBody JobHistoryDto jobHistoryDto,
            @PathVariable String username) throws Exception {
        System.out.println();
        return ResponseEntity.ok().body(jobHistoryService.save(jobHistoryDto, username));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("student")
    public void deleteJobHistory(@PathVariable int id) {
        jobHistoryService.delete(id);
    }
}
