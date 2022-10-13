package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.StudentDto;
import edu.miu.backend.dto.requestDto.StudentFilterQuery;
import edu.miu.backend.dto.responseDto.StudentResponseDto;
import edu.miu.backend.dto.responseDto.StudentsByCityResponse;
import edu.miu.backend.dto.responseDto.StudentsByStateResponse;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/student")
@CrossOrigin("*")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<StudentResponseDto>> getAll() {
        return ResponseEntity.ok().body(studentService.findAll());
    }

    @GetMapping("/filter")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<StudentResponseDto>> filterAllByQuery(@RequestParam(required = false) String state,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String major, @RequestParam(required = false) String name) {
        return ResponseEntity.ok()
                .body(studentService.filterStudentsByQuery(new StudentFilterQuery(state, city, major, name)));
    }

    @GetMapping("/{username}")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<StudentResponseDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(studentService.findByUsername(username));
    }

    @PutMapping("/{username}")
    @RolesAllowed("student")
    public ResponseEntity<StudentResponseDto> updateStudent(@ModelAttribute StudentDto studentDto,
            @PathVariable String username) throws Exception {
        return ResponseEntity.ok().body(studentService.update(studentDto, username));
    }

    @DeleteMapping("/{username}")
    public void deleteStudentByUsername(@PathVariable String username) {
        studentService.deleteByUsername(username);
    }

    @GetMapping("/count-by-city")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<StudentsByCityResponse>> countByCity() {
        return ResponseEntity.ok().body(studentService.findStudentsCountByCity());
    }

    @GetMapping("/count-by-state")
    @RolesAllowed({ "faculty", "student", "admin" })
    public ResponseEntity<List<StudentsByStateResponse>> countByState() {
        return ResponseEntity.ok().body(studentService.findStudentsCountByState());
    }
}
