package edu.miu.backend.service.impl;

import edu.miu.backend.dto.requestDto.CommentDto;
import edu.miu.backend.dto.responseDto.CommentResponseDto;
import edu.miu.backend.entity.Comment;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repository.CommentRepository;
import edu.miu.backend.repository.FacultyRepository;
import edu.miu.backend.repository.StudentRepository;
import edu.miu.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<CommentResponseDto> findAll() {
        List<Comment> tags = commentRepo.findAll();
        return tags.stream().map(comment -> {
            CommentResponseDto commentDto = modelMapper.map(comment, CommentResponseDto.class);
            commentDto.setStudentUsername(comment.getStudent().getUsername());
            commentDto.setFacultyUsername(comment.getFaculty().getUsername());
            return commentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponseDto> findByUsername(String username) {
        Optional<Student> student = studentRepo.findByUsername(username);
        if (!student.isPresent()) {
            return null;
        }
        return commentRepo.findByStudent(student.get())
                .stream()
                .map(comment -> {
                    CommentResponseDto commentDto = modelMapper.map(comment, CommentResponseDto.class);
                    commentDto.setStudentUsername(comment.getStudent().getUsername());
                    commentDto.setFacultyUsername(comment.getFaculty().getUsername());
                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto findById(int id) {
        Comment comment = commentRepo.findById(id).orElse(null);
        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto save(CommentDto commentDto) {
        System.out.println(commentDto);
        Student student = studentRepo.findByUsername(commentDto.getStudentUsername()).get();
        Faculty faculty = facultyRepo.findByUsername(commentDto.getFacultyUsername()).get();
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setStudent(student);
        comment.setFaculty(faculty);

        var savedCommentDto = modelMapper.map(commentRepo.save(comment), CommentResponseDto.class);
        savedCommentDto.setStudentUsername(student.getUsername());
        savedCommentDto.setFacultyUsername(faculty.getUsername());

        return savedCommentDto;
    }

    @Override
    public void delete(int id) {
        commentRepo.deleteById(id);
    }

    @Override
    public CommentResponseDto update(CommentDto commentDto, int id) throws Exception {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new Exception("Comment not found"));

        comment.setComment(commentDto.getComment());

        var savedCommentDto = modelMapper.map(commentRepo.save(comment), CommentResponseDto.class);
        savedCommentDto.setStudentUsername(comment.getStudent().getUsername());
        savedCommentDto.setFacultyUsername(comment.getFaculty().getUsername());

        return savedCommentDto;
    }

}
