package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.FacultyDto;
import edu.miu.backend.dto.responseDto.FacultyResponseDto;
import edu.miu.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/faculty")
@CrossOrigin("*")
public class FacultyController {
    private final FacultyService facultyService;

    @GetMapping("")
    public ResponseEntity<List<FacultyResponseDto>> getAll() {
        return ResponseEntity.ok().body(facultyService.findAll());
    }

    @GetMapping("/{username}")
    @RolesAllowed("admin")
    public ResponseEntity<FacultyResponseDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(facultyService.findByUsername(username));
    }

    @PutMapping("/{username}")
    @RolesAllowed("faculty")
    public ResponseEntity<FacultyResponseDto> updateFaculty(@RequestBody FacultyDto facultyDto,
            @PathVariable String username) throws Exception {
        return ResponseEntity.ok().body(facultyService.update(facultyDto, username));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("admin")
    public void deleteFaculty(@PathVariable String username) {
        facultyService.deleteByUsername(username);
    }

}
