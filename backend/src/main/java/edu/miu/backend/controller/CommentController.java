package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.CommentDto;
import edu.miu.backend.dto.responseDto.CommentResponseDto;
import edu.miu.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<CommentResponseDto>> getAll() {
        return ResponseEntity.ok().body(commentService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<CommentResponseDto>> getById(@PathVariable String username) {
        return ResponseEntity.ok().body(commentService.findByUsername(username));
    }

    @PostMapping()
    @RolesAllowed("faculty")
    public ResponseEntity<CommentResponseDto> addStudentComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok().body(commentService.save(commentDto));
    }

    @PutMapping("/{id}")
    @RolesAllowed("faculty")
    public ResponseEntity<CommentResponseDto> updateStudentComment(@RequestBody CommentDto commentDto,
            @PathVariable int id) throws Exception {
        return ResponseEntity.ok().body(commentService.update(commentDto, id));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("faculty")
    public String deleteStudentComment(@PathVariable int id) {
        commentService.delete(id);
        return "Deleted";
    }
}
